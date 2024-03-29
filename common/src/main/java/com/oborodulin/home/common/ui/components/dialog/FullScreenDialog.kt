package com.oborodulin.home.common.ui.components.dialog

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.field.util.Focusable
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.util.LogLevel.LOG_UI_COMPONENTS
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.home.common.util.OnListItemEvent
import timber.log.Timber

private const val TAG = "Common.ui.FullScreenDialog"

// https://stackoverflow.com/questions/65243956/jetpack-compose-fullscreen-dialog
// https://stackoverflow.com/questions/70622649/full-screen-dialog-in-android-compose-does-not-take-full-screen-height
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any, A : UiAction, E : UiSingleEvent, F : Focusable> FullScreenDialog(
    isShow: Boolean,
    viewModel: DialogViewModeled<T, A, E, F>,
    loadUiAction: A,
    confirmUiAction: A,
    dialogView: @Composable (T, () -> Unit) -> Unit,
    onDismissRequest: () -> Unit = {},
    onShowListDialog: () -> Unit = {},
    onValueChange: OnListItemEvent,
    dismissOnBackPress: Boolean = false,
    dismissOnClickOutside: Boolean = false,
    onConfirmButtonClick: () -> Unit = {}
) {
    if (LOG_UI_COMPONENTS) {
        Timber.tag(TAG).d("FullScreenDialog(...) called: isShow = %s", isShow)
    }
    if (isShow) {
        LaunchedEffect(Unit) {
            if (LOG_UI_COMPONENTS) {
                Timber.tag(TAG)
                    .d("SearchSingleSelectDialog -> LaunchedEffect()")
            }
            viewModel.submitAction(loadUiAction)
        }
        val handleConfirmAction = {
            // check for errors
            viewModel.onContinueClick {
                // if success,
                viewModel.handleActionJob({
                    // execute viewModel.Save()
                    viewModel.submitAction(confirmUiAction)
                }) {
                    // wait wile actionsJob executed
                    // hide single dialog and onConfirmButtonClick
                    viewModel.onDialogConfirm(onConfirmButtonClick)
                    val savedListItem = viewModel.savedListItem.value
                    if (LOG_UI_COMPONENTS) {
                        Timber.tag(TAG)
                            .d("Done: savedListItem = %s", savedListItem.itemId)
                    }
                    onValueChange(savedListItem)
                    // show list dialog (option)
                    onShowListDialog()
                }
            }
        }
        Dialog(
            onDismissRequest = {
                viewModel.onDialogDismiss(onDismissRequest)
                onShowListDialog()
            },
            properties = DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnClickOutside,
                usePlatformDefaultWidth = false // experimental
            )
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
                        if (LOG_UI_STATE) {
                            Timber.tag(TAG).d("Collect ui state flow: %s", state)
                        }
                        CommonScreen(state = state) {
                            TopAppBar(
                                title = {
                                    viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let { resId ->
                                        Text(stringResource(resId))
                                    }
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        viewModel.onDialogDismiss(onDismissRequest)
                                        onShowListDialog()
                                    }) {
                                        Icon(
                                            Icons.Outlined.Close,
                                            stringResource(R.string.dlg_close_cnt_desc)
                                        )
                                    }
                                }, actions = {
                                    IconButton(onClick = handleConfirmAction) {
                                        Icon(
                                            Icons.Outlined.Done,
                                            stringResource(R.string.dlg_done_cnt_desc)
                                        )
                                    }
                                })
                            Spacer(modifier = Modifier.height(8.dp))
                            dialogView(it, handleConfirmAction)
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewFullScreenDialog() {
    /*val items = listOf(ListItemModel.defaultListItemModel(LocalContext.current))
    val isShowDialog = remember { mutableStateOf(true) }

       FullScreenDialog(
           isShow = isShowDialog,
           viewModel =
       ) { item -> println(item.headline) }

     */
}
