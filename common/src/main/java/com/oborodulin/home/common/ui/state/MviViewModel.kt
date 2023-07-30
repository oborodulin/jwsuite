package com.oborodulin.home.common.ui.state

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.Searchable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Common.MviViewModel"

abstract class MviViewModel<T : Any, S : UiState<T>, A : UiAction, E : UiSingleEvent> :
    ViewModel(), MviViewModeled<T, A, E> {
    private val _uiStateFlow: MutableStateFlow<S> by lazy { MutableStateFlow(initState()) }

    private val _actionsFlow: MutableSharedFlow<A> = MutableSharedFlow()
    private val _actionsJobFlow: MutableSharedFlow<Job?> = MutableSharedFlow()
    val actionsJobFlow: SharedFlow<Job?> = _actionsJobFlow

    private val _singleEventFlow = Channel<E>()
    override val singleEventFlow = _singleEventFlow.receiveAsFlow()

    private val _searchText: MutableStateFlow<TextFieldValue> by lazy {
        MutableStateFlow(TextFieldValue(""))
    }
    final override val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    override val isSearching = _isSearching.asStateFlow()

    // https://www.youtube.com/watch?v=CfL6Dl2_dAE
    // https://stackoverflow.com/questions/70709121/how-to-convert-a-flowcustomtype-to-stateflowuistate-android-kotlin
    @OptIn(FlowPreview::class)
    override val uiStateFlow: StateFlow<S> = searchText
        .debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(_uiStateFlow) { query, uiState ->
            if (uiState !is UiState.Success<*>) uiState
            uiState as UiState.Success<*>
            if (uiState.data !is List<*>) uiState
            uiState.data as List<*>
            if (uiState.data.first() !is Searchable) uiState
            UiState.Success(data = uiState.data.filter {
                (it as Searchable).doesMatchSearchQuery(query.text)
            }) as S

        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000),
            _uiStateFlow.value
        )

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
                _actionsJobFlow.emit(job)
                Timber.tag(TAG).d("actionFlow.collect: emitted job = %s", job)
            }
        }
        Timber.tag(TAG).d("init ended")
    }

    abstract fun initState(): S

    override fun onSearchTextChange(text: TextFieldValue) {
        _searchText.value = text
    }

    abstract suspend fun handleAction(action: A): Job?

    abstract fun initFieldStatesByUiModel(uiModel: Any): Job?

    fun viewModelScope() = viewModelScope

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            _actionsJobFlow.collect { actionJob ->
                actionJob?.let { job ->
                    Timber.tag(TAG).d(
                        "handleActionJob(...): Start actionsJobFlow.collect [job = %s]",
                        job.toString()
                    )
                    if (job.isActive) job.join()
                    afterAction()
                }
            }
        }
        action()
        afterAction()
        Timber.tag(TAG).d("handleActionJob(...) ended")
    }

    override fun submitAction(action: A): Job {
        Timber.tag(TAG).d("submitAction(action): emit action = %s", action.javaClass.name)
        val job = viewModelScope.launch(errorHandler) {
            _actionsFlow.emit(action)
        }
        return job
    }

    fun submitState(state: S): Job {
        Timber.tag(TAG).d("submitState(S): change ui state = %s", state.javaClass.name)
        val job = viewModelScope.launch(errorHandler) {
            _uiStateFlow.value = state
            if (state is UiState.Success<*>) {
                Timber.tag(TAG).d("submitState: state.data = %s", state.data)
                initFieldStatesByUiModel(state.data)
            }
        }
        Timber.tag(TAG).d("submitState(S) ended")
        return job
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