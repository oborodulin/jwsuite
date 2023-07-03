package com.oborodulin.home.common.ui.state

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow

interface MviViewModeled<T : Any, A : UiAction> {
    val uiStateFlow: StateFlow<UiState<T>>
    fun submitAction(action: A): Job?
}