package com.oborodulin.home.common.ui.state

import androidx.annotation.StringRes
import com.oborodulin.home.common.ui.components.field.util.Focusable
import com.oborodulin.home.common.ui.model.ListItemModel
import kotlinx.coroutines.flow.StateFlow

// https://blog.logrocket.com/adding-alertdialog-jetpack-compose-android-apps/
interface DialogViewModeled<T : Any, A : UiAction, E : UiSingleEvent, F : Focusable> :
    SingleViewModeled<T, A, E, F> {
    val dialogTitleResId: StateFlow<Int?>
    val savedListItem: StateFlow<ListItemModel>
    val showDialog: StateFlow<Boolean>

    val areInputsValid: StateFlow<Boolean>

    fun setDialogTitleResId(@StringRes dialogTitleResId: Int)
    fun setSavedListItem(savedListItem: ListItemModel)
    fun onOpenDialogClicked()
    fun onDialogConfirm(onConfirm: () -> Unit)
    fun onDialogDismiss(onDismiss: () -> Unit)
}