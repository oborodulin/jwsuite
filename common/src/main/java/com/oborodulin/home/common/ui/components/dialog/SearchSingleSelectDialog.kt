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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.buttons.AddIconButtonComponent
import com.oborodulin.home.common.ui.components.list.items.SingleSelectListItemComponent
import com.oborodulin.home.common.ui.components.search.SearchComponent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.util.Constants.EMPTY_BLOCK
import com.oborodulin.home.common.util.LogLevel.LOG_UI_COMPONENTS
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.home.common.util.OnListItemEvent
import timber.log.Timber

private const val TAG = "Common.ui.SearchSingleSelectDialog"

@Composable
fun <T : ListItemModel, L : List<T>, A : UiAction, E : UiSingleEvent> SearchSingleSelectDialog(
    isShow: Boolean,
    title: String,
    viewModel: MviViewModeled<L, A, E>,
    loadUiAction: A,
    onDismissRequest: () -> Unit,
    onAddButtonClick: () -> Unit,
    onListItemClick: OnListItemEvent
) {
    if (isShow) {
        LaunchedEffect(Unit) {
            if (LOG_UI_COMPONENTS) Timber.tag(TAG)
                .d("SearchSingleSelectDialog -> LaunchedEffect() BEFORE collect ui state flow")
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
                    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
                    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
                    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
                        if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
                        CommonScreen(state = state) { items ->
                            if (items.isNotEmpty()) {
                                /*var searchState by rememberSaveable {
                                    mutableStateOf(TextFieldValue(searchText))
                                }*/
                                SearchComponent(
                                    searchText, onValueChange = viewModel::onSearchTextChange
                                )
                                var filteredItems: List<ListItemModel>
                                /*                                if (isSearching) {
                                                                    Box(modifier = Modifier.fillMaxSize()) {
                                                                        CircularProgressIndicator(
                                                                            modifier = Modifier.align(Alignment.Center)
                                                                        )
                                                                    }
                                                                } else {*/
                                LazyColumn(
                                    state = rememberLazyListState(),
                                    modifier = Modifier
                                        .selectableGroup() // Optional, for accessibility purpose
                                        .padding(8.dp)
                                        .focusable(enabled = true)
                                ) {
                                    val searchedText = searchText.text
                                    filteredItems = if (searchedText.isEmpty()) {
                                        items
                                    } else {
                                        items.filter { it.doesMatchSearchQuery(searchedText) }
                                    }
                                    items(filteredItems.size) { index ->
                                        SingleSelectListItemComponent(filteredItems[index]) { selectedItem ->
                                            if (LOG_UI_COMPONENTS) Timber.tag(TAG)
                                                .d("onClick() selectedItem = %s", selectedItem)
                                            onDismissRequest()
                                            onListItemClick(selectedItem)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (onAddButtonClick !== EMPTY_BLOCK) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            AddIconButtonComponent {
                                onDismissRequest()
                                onAddButtonClick()
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
