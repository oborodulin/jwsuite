package com.oborodulin.home.common.ui.state

import kotlinx.coroutines.flow.StateFlow

interface SingleViewModeled<T : Any, A : UiAction, E : UiSingleEvent> : MviViewModeled<T, A, E> {
    val isUiStateChanged: StateFlow<Boolean>
    fun onContinueClick(isPartialInputsValid: Boolean = false, onSuccess: () -> Unit)
}