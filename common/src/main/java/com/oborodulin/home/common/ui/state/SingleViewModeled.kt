package com.oborodulin.home.common.ui.state

interface SingleViewModeled<T : Any> : MviViewModeled<T> {
    fun onContinueClick(onSuccess: () -> Unit)
}