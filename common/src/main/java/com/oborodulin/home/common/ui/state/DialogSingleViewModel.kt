package com.oborodulin.home.common.ui.state

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import com.oborodulin.home.common.ui.components.field.util.Focusable
import com.oborodulin.home.common.ui.components.field.util.InputWrapped
import com.oborodulin.home.common.ui.model.ListItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

private const val TAG = "Common.DialogSingleViewModel"

// https://blog.logrocket.com/adding-alertdialog-jetpack-compose-android-apps/
abstract class DialogSingleViewModel<T : Any, S : UiState<T>, A : UiAction, E : UiSingleEvent, F : Focusable, W : InputWrapped>(
    state: SavedStateHandle,
    initFocusedTextField: Focusable? = null
) :
    DialogViewModeled<T, A, E>,
    SingleViewModel<T, S, A, E, F, W>(state, initFocusedTextField) {
    // https://medium.com/androiddevelopers/locale-changes-and-the-androidviewmodel-antipattern-84eb677660d9
    private val _dialogTitleResId: MutableStateFlow<Int?> = MutableStateFlow(null)
    override val dialogTitleResId = _dialogTitleResId.asStateFlow()

    private val _savedListItem: MutableStateFlow<ListItemModel> = MutableStateFlow(ListItemModel())
    override val savedListItem = _savedListItem.asStateFlow()

    // Initial value is false so the dialog is hidden
    private val _showDialog = MutableStateFlow(false)
    override val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    override fun setDialogTitleResId(@StringRes dialogTitleResId: Int) {
        Timber.tag(TAG).d("setDialogTitleResId() called: dialogTitleResId = %s", dialogTitleResId)
        _dialogTitleResId.value = dialogTitleResId
    }

    override fun setSavedListItem(savedListItem: ListItemModel) {
        Timber.tag(TAG).d("setSavedListItem() called: savedListItem = %s", savedListItem)
        _savedListItem.value = savedListItem
    }

    override fun onOpenDialogClicked() {
        Timber.tag(TAG).d("onOpenDialogClicked() called")
        _showDialog.value = true
    }

    override fun onDialogConfirm(onConfirm: () -> Unit) {
        Timber.tag(TAG).d("onDialogConfirm() called")
        _showDialog.value = false
        onConfirm()
    }

    override fun onDialogDismiss(onDismiss: () -> Unit) {
        Timber.tag(TAG).d("onDialogDismiss() called")
        _showDialog.value = false
        onDismiss()
    }
}