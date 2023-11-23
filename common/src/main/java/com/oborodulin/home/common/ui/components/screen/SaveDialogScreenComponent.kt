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
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.components.dialog.alert.CancelChangesConfirmDialogComponent
import com.oborodulin.home.common.ui.components.dialog.alert.ErrorAlertDialogComponent
import com.oborodulin.home.common.ui.components.field.util.Focusable
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.home.common.ui.state.UiSingleEvent
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
    isCancelChangesShowAlert: MutableState<Boolean>,
    @StringRes cancelChangesConfirmResId: Int,
    @StringRes uniqueConstraintFailedResId: Int? = null,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit,
    dialogView: @Composable (T) -> Unit
) {
    Timber.tag(TAG).d("SaveDialogScreenComponent(...) called: inputId = %s", inputId)
    val ctx = LocalContext.current
    var errorMessage: String? by rememberSaveable { mutableStateOf(null) }
    //val errorMessage by viewModel.uiStateErrorMsg.collectAsStateWithLifecycle()
    val isErrorShowAlert = rememberSaveable { mutableStateOf(false) }
    val handleSaveButtonClick = {
        Timber.tag(TAG).d("SaveDialogScreenComponent: Save Button click...")
        viewModel.onContinueClick {
            viewModel.handleActionJob({ viewModel.submitAction(saveUiAction) }) {
                errorMessage = viewModel.redirectedErrorMessage()
                if (errorMessage == null) {
                    upNavigation()
                } else {
                    isErrorShowAlert.value = true
                    errorMessage = when {
                        errorMessage!!.contains("SQLiteConstraintException: error code 19 (extended error code 2067)") -> uniqueConstraintFailedResId?.let {
                            ctx.resources.getString(it)
                        }.orEmpty()

                        else -> errorMessage
                    }
                }
            }
        }
    }
    LaunchedEffect(inputId) {
        Timber.tag(TAG)
            .d("SaveDialogScreenComponent -> LaunchedEffect(inputId): inputId = %s", inputId)
        inputId?.let { viewModel.submitAction(loadUiAction) }
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
            onActionBarSubtitleChange(stringResource(it))
        }
        // Cancel Changes Confirm:
        CancelChangesConfirmDialogComponent(
            isShow = isCancelChangesShowAlert,
            text = stringResource(cancelChangesConfirmResId),
            onConfirm = upNavigation
        )
        // Error Alert:
        ErrorAlertDialogComponent(isShow = isErrorShowAlert, text = errorMessage) {
            isErrorShowAlert.value = false; upNavigation()
        }
        onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
        val areInputsValid by viewModel.areInputsValid.collectAsStateWithLifecycle()
        onTopBarActionsChange {
            IconButton(enabled = areInputsValid, onClick = handleSaveButtonClick) {
                Icon(Icons.Outlined.Done, stringResource(R.string.dlg_done_cnt_desc))
            }
        }
        CommonScreen(state = state) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                dialogView.invoke(it)
                // https://developer.android.com/guide/topics/resources/more-resources#Dimension
                Spacer(Modifier.height(8.dp))
                SaveButtonComponent(enabled = areInputsValid, onClick = handleSaveButtonClick)
            }
        }
    }
}