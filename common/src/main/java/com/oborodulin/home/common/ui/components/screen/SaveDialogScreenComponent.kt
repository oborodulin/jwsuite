package com.oborodulin.home.common.ui.components.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.components.field.util.Focusable
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.util.LogLevel.LOG_UI_COMPONENTS
import timber.log.Timber
import java.util.UUID

private const val TAG = "Common.ui.SaveDialogScreenComponent"

@Composable
fun <T : Any, A : UiAction, E : UiSingleEvent, F : Focusable> SaveDialogScreenComponent(
    viewModel: DialogViewModeled<T, A, E, F>,
    inputId: UUID? = null,
    loadUiAction: A,
    saveUiAction: A,
    upNavigation: () -> Unit,
    handleTopBarNavClick: MutableState<() -> Unit>,
    @StringRes cancelChangesConfirmResId: Int,
    @StringRes uniqueConstraintFailedResId: Int? = null,
    /*onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,*/
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit,
    //onFabChange: (@Composable () -> Unit) -> Unit,
    innerPadding: PaddingValues,
    dialogView: @Composable (T) -> Unit
) {
    if (LOG_UI_COMPONENTS) Timber.tag(TAG)
        .d("SaveDialogScreenComponent(...) called: inputId = %s", inputId)
    DialogScreenComponent(
        viewModel = viewModel,
        inputId = inputId,
        loadUiAction = loadUiAction,
        saveUiAction = saveUiAction,
        upNavigation = upNavigation,
        handleTopBarNavClick = handleTopBarNavClick,
        cancelChangesConfirmResId = cancelChangesConfirmResId,
        uniqueConstraintFailedResId = uniqueConstraintFailedResId,
        confirmButton = { areValid, handleSaveButtonClick ->
            SaveButtonComponent(enabled = areValid, onClick = handleSaveButtonClick)
        },
        /*onActionBarChange = onActionBarChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,*/
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarActionsChange = onTopBarActionsChange,
        //onFabChange = onFabChange,
        innerPadding = innerPadding,
        dialogView = dialogView
    )
}