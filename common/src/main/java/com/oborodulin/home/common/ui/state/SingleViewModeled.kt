package com.oborodulin.home.common.ui.state

interface SingleViewModeled<T : Any, A : UiAction> : MviViewModeled<T, A> {
    var dialogTitleResId: Int?
    fun onContinueClick(onSuccess: () -> Unit)
}