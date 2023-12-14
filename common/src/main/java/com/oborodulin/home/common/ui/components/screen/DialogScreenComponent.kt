package com.oborodulin.home.common.ui.components.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.dialog.alert.CancelChangesConfirmDialogComponent
import com.oborodulin.home.common.ui.components.dialog.alert.ErrorAlertDialogComponent
import com.oborodulin.home.common.ui.components.field.util.Focusable
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_JOB
import com.oborodulin.home.common.util.LogLevel.LOG_UI_COMPONENTS
import kotlinx.coroutines.cancel
import timber.log.Timber
import java.util.UUID

private const val TAG = "Common.ui.DialogScreenComponent"

@Composable
fun <T : Any, A : UiAction, E : UiSingleEvent, F : Focusable> DialogScreenComponent(
    viewModel: DialogViewModeled<T, A, E, F>,
    inputId: UUID? = null,
    loadUiAction: A,
    saveUiAction: A,
    nextUiAction: A? = null,
    nextAction: (() -> Unit)? = null,
    upNavigation: () -> Unit,
    handleTopBarNavClick: MutableState<() -> Unit>,
    topBarActionImageVector: ImageVector = Icons.Outlined.Done,
    @StringRes topBarActionCntDescResId: Int = R.string.dlg_done_cnt_desc,
    isControlsShow: Boolean = true,
    areInputsValid: Boolean = viewModel.areInputsValid.collectAsStateWithLifecycle().value,
    @StringRes cancelChangesConfirmResId: Int,
    @StringRes uniqueConstraintFailedResId: Int? = null,
    confirmButton: @Composable (Boolean, () -> Unit) -> Unit,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit,
    dialogView: @Composable (T) -> Unit
) {
    if (LOG_UI_COMPONENTS) Timber.tag(TAG)
        .d("DialogScreenComponent(...) called: inputId = %s", inputId)
    val ctx = LocalContext.current
    var errorMessage: String? by rememberSaveable { mutableStateOf(null) }
    //val errorMessage by viewModel.uiStateErrorMsg.collectAsStateWithLifecycle()
    val isErrorShowAlert = rememberSaveable { mutableStateOf(false) }
    val handleTopBarActionButtonClick = {
        if (LOG_UI_COMPONENTS) Timber.tag(TAG)
            .d("DialogScreenComponent: Top Bar Action Button click...")
        viewModel.onContinueClick {
            // https://stackoverflow.com/questions/72987545/how-to-navigate-to-another-screen-after-call-a-viemodelscope-method-in-viewmodel
            viewModel.handleActionJob({ viewModel.submitAction(saveUiAction) }) { scope ->
                errorMessage = viewModel.redirectedErrorMessage()
                if (errorMessage == null) {
                    if (LOG_FLOW_JOB) Timber.tag(TAG)
                        .d(
                            "DialogScreenComponent -> viewModel.onContinueClick -> After action: (no errors) nextUiAction = %s",
                            nextUiAction
                        )
                    nextUiAction?.let { viewModel.submitAction(it) }
                        ?: nextAction?.invoke() ?: upNavigation()
                } else {
                    isErrorShowAlert.value = true
                    errorMessage = when {
                        errorMessage!!.contains("SQLiteConstraintException:") -> uniqueConstraintFailedResId?.let {
                            ctx.resources.getString(it)
                        }.orEmpty()

                        else -> errorMessage
                    }
                    if (LOG_FLOW_JOB) Timber.tag(TAG)
                        .d("DialogScreenComponent -> viewModel.onContinueClick -> After action: cancel flow by errors")
                    scope.cancel()
                }
            }
        }
    }
    LaunchedEffect(inputId) {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("DialogScreenComponent -> LaunchedEffect(inputId): inputId = %s", inputId)
        //inputId?.let {
        viewModel.submitAction(loadUiAction)
        //}
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_COMPONENTS) Timber.tag(TAG).d("Collect ui state flow: %s", state)
        viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
            onActionBarSubtitleChange(stringResource(it))
        }
        // Cancel Changes Confirm:
        val isUiStateChanged by viewModel.isUiStateChanged.collectAsStateWithLifecycle()
        val isCancelChangesShowAlert = rememberSaveable { mutableStateOf(false) }
        handleTopBarNavClick.value =
            { if (isUiStateChanged) isCancelChangesShowAlert.value = true else upNavigation() }
        CancelChangesConfirmDialogComponent(
            isShow = isCancelChangesShowAlert,
            text = stringResource(cancelChangesConfirmResId),
            onConfirm = upNavigation
        )
        // Error Alert:
        ErrorAlertDialogComponent(isShow = isErrorShowAlert, text = errorMessage) {
            isErrorShowAlert.value = false; upNavigation()
        }
        onActionBarChange(null)
        onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
        if (isControlsShow) {
            onTopBarActionsChange(true) {
                IconButton(enabled = areInputsValid, onClick = handleTopBarActionButtonClick) {
                    Icon(topBarActionImageVector, stringResource(topBarActionCntDescResId))
                }
            }
        } else {
            onTopBarActionsChange(true) {}
        }
        onFabChange {}
        CommonScreen(state = state) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                dialogView.invoke(it)
                if (isControlsShow) {
                    // https://developer.android.com/guide/topics/resources/more-resources#Dimension
                    Spacer(Modifier.height(8.dp))
                    confirmButton.invoke(areInputsValid, handleTopBarActionButtonClick)
                }
            }
        }
    }
}