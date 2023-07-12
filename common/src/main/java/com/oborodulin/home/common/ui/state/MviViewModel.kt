package com.oborodulin.home.common.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Common.MviViewModel"

abstract class MviViewModel<T : Any, S : UiState<T>, A : UiAction, E : UiSingleEvent> :
    ViewModel(), MviViewModeled<T, A, E> {
    private val _uiStateFlow: MutableStateFlow<S> by lazy { MutableStateFlow(initState()) }
    override val uiStateFlow: StateFlow<S> = _uiStateFlow

    private val _actionsFlow: MutableSharedFlow<A> = MutableSharedFlow()
    private val _actionsJobFlow: MutableSharedFlow<Job?> = MutableSharedFlow()
    val actionsJobFlow: SharedFlow<Job?> = _actionsJobFlow

    private val _singleEventFlow = Channel<E>()
    override val singleEventFlow = _singleEventFlow.receiveAsFlow()

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