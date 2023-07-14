package com.oborodulin.home.common.ui.state

import androidx.annotation.StringRes
import com.oborodulin.home.common.ui.model.ListItemModel
import kotlinx.coroutines.flow.StateFlow

// https://blog.logrocket.com/adding-alertdialog-jetpack-compose-android-apps/
interface DialogViewModeled<T : Any, A : UiAction, E : UiSingleEvent> : SingleViewModeled<T, A, E> {
    val dialogTitleResId: StateFlow<Int?>
    val savedListItem: StateFlow<ListItemModel>
    val showDialog: StateFlow<Boolean>
    fun setDialogTitleResId(@StringRes dialogTitleResId: Int)
    fun setSavedListItem(savedListItem: ListItemModel)
    fun onOpenDialogClicked()
    fun onDialogConfirm(onConfirm: () -> Unit)
    fun onDialogDismiss(onDismiss: () -> Unit)
}