package com.oborodulin.home.common.ui.components.dialog

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.MutableState
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
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.state.SingleViewModeled
import timber.log.Timber

private const val TAG = "Common.ui.FullScreenDialog"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> FullScreenDialog(
    isShow: MutableState<Boolean>,
    title: String,
    viewModel: SingleViewModeled<T>,
    dialogView: @Composable (T) -> Unit,
    onDismissRequest: (() -> Unit)? = null,
    dismissOnBackPress: Boolean = false,
    dismissOnClickOutside: Boolean = false,
    onSaveButtonClick: () -> Unit
) {
    if (isShow.value) {
        Dialog(
            onDismissRequest = onDismissRequest ?: { isShow.value = false },
            properties = DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnClickOutside,
                usePlatformDefaultWidth = false // experimental
            )
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                    Text(text = title)
                    Spacer(modifier = Modifier.height(10.dp))
                    viewModel.uiStateFlow.collectAsState().value.let { state ->
                        Timber.tag(TAG).d("Collect ui state flow: %s", state)
                        CommonScreen(state = state) {
                            TopAppBar(
                                title = { Text(title) },
                                navigationIcon = {
                                    IconButton(onClick = onDismissRequest ?: {
                                        isShow.value = false
                                    }) {
                                        Icon(Icons.Outlined.Close, null)
                                    }
                                }, actions = {
                                    IconButton(onClick = {
                                        viewModel.onContinueClick(onSaveButtonClick)
                                    }) {
                                        Icon(Icons.Outlined.Done, null)
                                    }
                                })
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

    FullScreenDialog(
        isShow = isShowDialog,
        title = stringResource(R.string.preview_blank_title),
        viewModel =
    ) { item -> println(item.headline) }
}
