package com.oborodulin.home.common.ui.state

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Common.MviViewModel"

abstract class MviViewModel<T : Any, S : UiState<T>, A : UiAction, E : UiSingleEvent> : ViewModel(),
    MviViewModeled<T, A, E> {
    private val _uiStateFlow: MutableStateFlow<S> by lazy { MutableStateFlow(initState()) }
    override val uiStateFlow = _uiStateFlow

    private val _uiStateErrorMsg: MutableStateFlow<String?> = MutableStateFlow(null)
    override val uiStateErrorMsg = _uiStateErrorMsg.asStateFlow()

    private val _actionsFlow: MutableSharedFlow<A> = MutableSharedFlow()

    private val _actionsJobFlow: MutableSharedFlow<Job?> = MutableSharedFlow()
    override val actionsJobFlow: SharedFlow<Job?> = _actionsJobFlow

    private val _singleEventFlow = Channel<E>()
    override val singleEventFlow = _singleEventFlow.receiveAsFlow()

    private val _searchText: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue(""))
    override val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    override val isSearching = _isSearching.asStateFlow()

    val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.tag(TAG).e(exception)
        //_uiState.value = _uiState.value.copy(error = exception.message, isLoading = false)
    }

    init {
        Timber.tag(TAG).d("init called")
        viewModelScope.launch(errorHandler) {
            Timber.tag(TAG).d("init: Start actionFlow.collect")
            _actionsFlow.collect {
                val job = handleAction(it)
                if (it.isEmitJob) {
                    _actionsJobFlow.emit(job)
                    Timber.tag(TAG).d("actionFlow.collect: emitted job = %s", job)
                }
            }
        }
        Timber.tag(TAG).d("init ended")
    }

    abstract fun initState(): S

    private fun redirectUiStateErrorMessage(errorMessage: String?) {
        Timber.tag(TAG).d("redirectUiStateErrorMessage(...) called: errorMessage = %s", errorMessage)
        _uiStateErrorMsg.value = errorMessage
    }

    private fun clearUiStateErrorMessage() {
        Timber.tag(TAG).d("clearUiStateErrorMessage() called")
        redirectUiStateErrorMessage(null)
    }

    override fun onSearchTextChange(text: TextFieldValue) {
        _searchText.value = text
    }

    abstract suspend fun handleAction(action: A): Job?

    abstract fun initFieldStatesByUiModel(uiModel: T): Job?

    fun viewModelScope() = viewModelScope

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {
        Timber.tag(TAG).d("handleActionJob(...) called")
        //viewModelScope.launch(Dispatchers.Main) {
        // https://stackoverflow.com/questions/72987545/how-to-navigate-to-another-screen-after-call-a-viemodelscope-method-in-viewmodel
        viewModelScope.launch(errorHandler) {
            _actionsJobFlow.collectLatest { job ->
                Timber.tag(TAG).d(
                    "handleActionJob(...): Start actionsJobFlow.collectLatest [job = %s]",
                    job?.toString()
                )
                job?.join()
                afterAction()
            }
        }
        clearUiStateErrorMessage()
        action()
        Timber.tag(TAG).d("handleActionJob(...) ended")
    }

    override fun submitAction(action: A): Job {
        Timber.tag(TAG).d("submitAction(action): emit action = %s", action.javaClass.name)
        val job = viewModelScope.launch(errorHandler) {
            _actionsFlow.emit(action)
        }
        return job
    }

    open fun submitState(state: S): Job {
        Timber.tag(TAG).d("submitState(S): ui state = %s", state.javaClass.name)
        return submitStateWithErrorStateMessageRedirection(state, false)
    }

    fun submitStateWithErrorStateMessageRedirection(
        state: S, redirectErrorStateMessage: Boolean
    ): Job {
        Timber.tag(TAG).d(
            "submitStateWithErrorStateMessageRedirection(S): redirectErrorStateMessage = %s; change ui state = %s",
            redirectErrorStateMessage,
            state.javaClass.name
        )
        val job = viewModelScope.launch(errorHandler) {
            if (!redirectErrorStateMessage) {
                _uiStateFlow.value = state
            }
            uiState(state)?.let {
                if (redirectErrorStateMessage) {
                    _uiStateFlow.value = state
                }
                Timber.tag(TAG)
                    .d("submitStateWithErrorStateMessageRedirection: state.data = %s", it)
                initFieldStatesByUiModel(it)
            }
        }
        Timber.tag(TAG).d("submitStateWithErrorStateMessageRedirection(S) ended")
        return job
    }

    fun uiState(state: S? = null): T? {
        Timber.tag(TAG).d("uiState(S?) called")
        clearUiStateErrorMessage()
        return when (val uiState = state ?: _uiStateFlow.value) {
            is UiState.Success<*> -> (uiState as UiState.Success<*>).data as T

            is UiState.Error -> {
                redirectUiStateErrorMessage((uiState as UiState.Error).errorMessage); null
            }

            else -> null
        }
    }

    fun submitSingleEvent(event: E): Job {
        Timber.tag(TAG).d("submitSingleEvent(E): send single event = %s", event.javaClass.name)
        val job = viewModelScope.launch(errorHandler) {
            _singleEventFlow.send(event)
        }
        Timber.tag(TAG).d("submitSingleEvent(E) ended")
        return job
    }
}