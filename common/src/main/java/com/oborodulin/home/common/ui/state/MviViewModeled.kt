package com.oborodulin.home.common.ui.state

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MviViewModeled<T : Any, A : UiAction, E : UiSingleEvent> {
    val uiStateFlow: StateFlow<UiState<T>>
    val singleEventFlow: Flow<E>

    fun submitAction(action: A): Job?
}