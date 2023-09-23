package com.oborodulin.home.common.ui.state

interface SingleViewModeled<T : Any, A : UiAction, E : UiSingleEvent> : MviViewModeled<T, A, E> {
    fun onContinueClick(isPartialInputsValid: Boolean = false, onSuccess: () -> Unit)
}