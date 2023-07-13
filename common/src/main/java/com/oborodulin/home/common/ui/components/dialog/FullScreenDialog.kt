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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.home.common.ui.state.UiSingleEvent
import timber.log.Timber

private const val TAG = "Common.ui.FullScreenDialog"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any, A : UiAction, E : UiSingleEvent> FullScreenDialog(
    isShow: Boolean,
    viewModel: DialogViewModeled<T, A, E>,
    loadUiAction: A,
    dialogView: @Composable (T) -> Unit,
    onDismissRequest: () -> Unit = {},
    onShowListDialog: () -> Unit = {},
    dismissOnBackPress: Boolean = false,
    dismissOnClickOutside: Boolean = false,
    onConfirmButtonClick: () -> Unit
) {
    Timber.tag(TAG).d("FullScreenDialog(...) called: isShow = %s", isShow)
    if (isShow) {
        LaunchedEffect(Unit) {
            Timber.tag(TAG)
                .d("SearchSingleSelectDialog: LaunchedEffect() BEFORE collect ui state flow")
            viewModel.submitAction(loadUiAction)
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
                    viewModel.uiStateFlow.collectAsState().value.let { state ->
                        Timber.tag(TAG).d("Collect ui state flow: %s", state)
                        CommonScreen(state = state) {
                            TopAppBar(
                                title = {
                                    viewModel.dialogTitleResId.collectAsState().value?.let { resId ->
                                        Text(stringResource(resId))
                                    }
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        viewModel.onDialogDismiss(onDismissRequest)
                                        onShowListDialog()
                                    }) {
                                        Icon(Icons.Outlined.Close, null)
                                    }
                                }, actions = {
                                    IconButton(onClick = {
                                        // check for errors
                                        viewModel.onContinueClick {
                                            // if success, hide dialog and execute onConfirmButtonClick: viewModel.Save()
                                            viewModel.onDialogConfirm(onConfirmButtonClick)
                                        }
                                        onShowListDialog()
                                    }) {
                                        Icon(Icons.Outlined.Done, null)
                                    }
                                })
                            Spacer(modifier = Modifier.height(8.dp))
                            dialogView(it)
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
    val items = listOf(ListItemModel.defaultListItemModel(LocalContext.current))
    val isShowDialog = remember { mutableStateOf(true) }

    /*   FullScreenDialog(
           isShow = isShowDialog,
           viewModel =
       ) { item -> println(item.headline) }

     */
}
