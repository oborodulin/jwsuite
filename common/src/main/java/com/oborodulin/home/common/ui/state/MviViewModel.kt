package com.oborodulin.home.common.ui.state

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_EVENT
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_JOB
import com.oborodulin.home.common.util.LogLevel.LOG_MVI_UI_STATE
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Common.MviViewModel"

abstract class MviViewModel<T : Any, S : UiState<T>, A : UiAction, E : UiSingleEvent> : ViewModel(),
    MviViewModeled<T, A, E> {
    protected val _uiStateFlow: MutableStateFlow<S> by lazy { MutableStateFlow(initState()) }
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
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG).d("init called: %s", isInitialized)
        }
        isInitialized = true
        viewModelScope.launch(errorHandler) {
            if (LOG_FLOW_ACTION) {
                Timber.tag(TAG).d(
                    "AF# init: Start actionFlow.collect on thread '%s':",
                    Thread.currentThread().name
                )
            }
            _actionsFlow.collect {
                val job = handleAction(it)
                if (it.isEmitJob) {
                    _actionsJobFlow.emit(job)
                    if (LOG_FLOW_ACTION || LOG_FLOW_JOB) {
                        Timber.tag(TAG)
                            .d("AF# init -> actionFlow.collect: emitted job = %s", job)
                    }
                }
            }
        }
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG).d("AF# init ended")
        }
    }

    abstract fun initState(): S

    private fun redirectUiStateErrorMessage(errorMessage: String?) {
        if (LOG_MVI_UI_STATE) {
            Timber.tag(TAG)
                .d("redirectUiStateErrorMessage(...) called: errorMessage = %s", errorMessage)
        }
        _uiStateErrorMsg.value = errorMessage
    }

    override fun redirectedErrorMessage() = _uiStateErrorMsg.value

    private fun clearUiStateErrorMessage() {
        if (LOG_MVI_UI_STATE) {
            Timber.tag(TAG).d("clearUiStateErrorMessage() called")
        }
        redirectUiStateErrorMessage(null)
    }

    override fun onSearchTextChange(text: TextFieldValue) {
        _searchText.value = text
    }

    override fun clearSearchText() {
        onSearchTextChange(TextFieldValue(""))
    }

    abstract suspend fun handleAction(action: A): Job?

    abstract fun initFieldStatesByUiModel(uiModel: T): Job?

    fun viewModelScope() = viewModelScope

    override fun handleActionJob(action: () -> Unit, afterAction: (CoroutineScope) -> Unit) {
        if (LOG_FLOW_JOB) {
            Timber.tag(TAG)
                .d("JF# handleActionJob(...) called on thread '%s':", Thread.currentThread().name)
        }
        //viewModelScope.launch(Dispatchers.Main) {
        // https://stackoverflow.com/questions/72987545/how-to-navigate-to-another-screen-after-call-a-viemodelscope-method-in-viewmodel
        viewModelScope.launch(errorHandler) {
            _actionsJobFlow.collectLatest { job ->
                if (LOG_FLOW_JOB) {
                    Timber.tag(TAG).d(
                        "JF# handleActionJob: Start actionsJobFlow.collectLatest [job = %s]",
                        job?.toString()
                    )
                }
                job?.join()
                afterAction(this)
                if (this.isActive) this.cancel()
            }
        }
        clearUiStateErrorMessage()
        action()
        if (LOG_FLOW_JOB) {
            Timber.tag(TAG).d("JF# handleActionJob(...) ended")
        }
    }

    override fun submitAction(action: A): Job {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG).d("AF# submitAction(action): emit action = %s", action.javaClass.name)
        }
        val job = viewModelScope.launch(errorHandler) {
            _actionsFlow.emit(action)
        }
        return job
    }

    open fun submitState(state: S): Job {
        if (LOG_MVI_UI_STATE) {
            Timber.tag(TAG)
                .d("submitState(S): ui state = %s", state.javaClass.name)
        }
        return submitStateWithErrorStateMessageRedirection(state, false)
    }

    fun submitStateWithErrorStateMessageRedirection(
        state: S, redirectErrorStateMessage: Boolean
    ): Job {
        if (LOG_MVI_UI_STATE) {
            Timber.tag(TAG).d(
                "submitStateWithErrorStateMessageRedirection(S): redirectErrorStateMessage = %s; change ui state = %s",
                redirectErrorStateMessage, state.javaClass.name
            )
        }
        val job = viewModelScope.launch(errorHandler) {
            if (!redirectErrorStateMessage) {
                _uiStateFlow.value = state
            }
            uiState(state)?.let {
                if (redirectErrorStateMessage) {
                    _uiStateFlow.value = state
                }
                if (LOG_MVI_UI_STATE) {
                    Timber.tag(TAG)
                        .d("submitStateWithErrorStateMessageRedirection: state.data = %s", it)
                }
                initFieldStatesByUiModel(it)
            }
        }
        if (LOG_MVI_UI_STATE) {
            Timber.tag(TAG)
                .d("submitStateWithErrorStateMessageRedirection(S) ended")
        }
        return job
    }

    fun uiState(state: S? = null): T? {
        if (LOG_MVI_UI_STATE) {
            Timber.tag(TAG).d("uiState(S?) called")
        }
        //clearUiStateErrorMessage()
        return when (val uiState = state ?: _uiStateFlow.value) {
            is UiState.Success<*> -> (uiState as UiState.Success<*>).data as T

            is UiState.Error -> {
                redirectUiStateErrorMessage((uiState as UiState.Error).errorMessage); null
            }

            else -> null
        }
    }

    fun submitSingleEvent(event: E): Job {
        if (LOG_FLOW_EVENT) {
            Timber.tag(TAG)
                .d("EF# submitSingleEvent(E): send single event = %s", event.javaClass.name)
        }
        val job = viewModelScope.launch(errorHandler) {
            _singleEventFlow.send(event)
        }
        if (LOG_FLOW_EVENT) {
            Timber.tag(TAG).d("EF# submitSingleEvent(E) ended")
        }
        return job
    }

    companion object {
        @Volatile
        var isInitialized: Boolean = false
    }
}