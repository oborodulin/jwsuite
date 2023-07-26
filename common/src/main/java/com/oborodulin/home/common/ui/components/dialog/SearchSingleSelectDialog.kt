package com.oborodulin.home.common.ui.components.dialog

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.items.SingleSelectListItemComponent
import com.oborodulin.home.common.ui.components.search.SearchComponent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.util.OnListItemEvent
import timber.log.Timber
import java.util.Locale

private const val TAG = "Common.ui.SearchSingleSelectDialog"

@Composable
fun <T : List<*>, A : UiAction, E : UiSingleEvent> SearchSingleSelectDialog(
    isShow: Boolean,
    title: String,
    searchedItem: String = "",
    viewModel: MviViewModeled<T, A, E>,
    loadUiAction: A,
    onDismissRequest: () -> Unit,
    onAddButtonClick: () -> Unit,
    onListItemClick: OnListItemEvent
) {
    if (isShow) {
        LaunchedEffect(Unit) {
            Timber.tag(TAG)
                .d("SearchSingleSelectDialog: LaunchedEffect() BEFORE collect ui state flow")
            viewModel.submitAction(loadUiAction)
        }
        Dialog(onDismissRequest = onDismissRequest) {
            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = title)
                    Spacer(modifier = Modifier.height(10.dp))
                    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
                        Timber.tag(TAG).d("Collect ui state flow: %s", state)
                        CommonScreen(state = state) { items ->
                            items as List<ListItemModel>
                            if (items.isNotEmpty()) {
                                var searchState by rememberSaveable {
                                    mutableStateOf(TextFieldValue(searchedItem))
                                }
                                SearchComponent(searchState) { searchState = it }
                                var filteredItems: List<ListItemModel>
                                LazyColumn(
                                    state = rememberLazyListState(),
                                    modifier = Modifier
                                        .selectableGroup() // Optional, for accessibility purpose
                                        .padding(8.dp)
                                        .focusable(enabled = true)
                                ) {
                                    val searchedText = searchState.text
                                    filteredItems = if (searchedText.isEmpty()) {
                                        items
                                    } else {
                                        val resultList = mutableListOf<ListItemModel>()
                                        for (item in items) {
                                            if (item.headline.lowercase(Locale.getDefault())
                                                    .contains(searchedText.lowercase(Locale.getDefault()))
                                            ) {
                                                resultList.add(item)
                                            }
                                        }
                                        resultList
                                    }
                                    items(filteredItems.size) { index ->
                                        SingleSelectListItemComponent(filteredItems[index]) { selectedItem ->
                                            Timber.tag(TAG)
                                                .d("onClick() selectedItem = %s", selectedItem)
                                            onDismissRequest()
                                            onListItemClick(selectedItem)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = {
                            onDismissRequest()
                            onAddButtonClick()
                        }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    modifier = Modifier.size(36.dp),
                                    imageVector = Icons.Outlined.Add,
                                    contentDescription = ""
                                )
                                Spacer(modifier = Modifier.width(width = 8.dp))
                                Text(text = stringResource(R.string.btn_add_lbl))
                            }
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
fun PreviewSearchSingleSelectDialog() {
    /*    val items = listOf(ListItemModel.defaultListItemModel(LocalContext.current))
        val isShowDialog = remember { mutableStateOf(true) }
        var isShowFullScreenDialog by remember { mutableStateOf(false) }

        if (isShowFullScreenDialog) {
            LocalContext.current.toast("another Full-screen Dialog")
        }

        SearchSingleSelectDialog(
            isShow = isShowDialog,
            title = stringResource(R.string.preview_blank_title),
            viewModel =
            onAddButtonClick = { isShowFullScreenDialog = true }
        ) { item -> println(item.headline) }

         */
}
