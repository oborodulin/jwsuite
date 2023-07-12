package com.oborodulin.home.common.ui.state

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.StateFlow

// https://blog.logrocket.com/adding-alertdialog-jetpack-compose-android-apps/
interface DialogViewModeled<T : Any, A : UiAction, E : UiSingleEvent> : SingleViewModeled<T, A, E> {
    val dialogTitleResId: StateFlow<Int?>
    val showDialog: StateFlow<Boolean>
    fun setDialogTitleResId(@StringRes dialogTitleResId: Int)
    fun onOpenDialogClicked()
    fun onDialogConfirm(onConfirm: () -> Unit)
    fun onDialogDismiss(onDismiss: () -> Unit)
}