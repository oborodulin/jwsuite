package com.oborodulin.home.common.ui.components.list

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.EmptyListTextComponent
import com.oborodulin.home.common.ui.components.buttons.AddIconButtonComponent
import com.oborodulin.home.common.ui.components.dialog.FullScreenDialog
import com.oborodulin.home.common.ui.components.list.items.CheckedListItemComponent
import com.oborodulin.home.common.ui.components.search.SearchComponent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import timber.log.Timber

private const val TAG = "Common.ui"

@Composable
fun <T : Any, L : List<ListItemModel>, LA : UiAction, SA : UiAction, E : UiSingleEvent> SearchMultiCheckViewComponent(
    listViewModel: MviViewModeled<L, LA, E>,        // territoryHouseViewModel
    loadListUiAction: LA,                            // TerritoryHouseUiAction.Load(it.territoryId)
    items: List<ListItemModel>,
    singleViewModel: DialogViewModeled<T, SA, E>,    // houseViewModel
    loadUiAction: SA,                                // HouseUiAction.Load()
    confirmUiAction: SA,                             // HouseUiAction.Save
    @StringRes emptyListTextResId: Int,
    dialogView: @Composable (T) -> Unit
) {
    Timber.tag(TAG).d("SearchMultiCheckViewComponent(...) called")
    val searchText by listViewModel.searchText.collectAsStateWithLifecycle()
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = loadUiAction,
        confirmUiAction = confirmUiAction,
        dialogView = dialogView,
        onValueChange = {
            listViewModel.submitAction(loadListUiAction)
        },
        //onShowListDialog = onShowListDialog
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SearchComponent(
                searchText,
                modifier = Modifier.weight(2.8f),
                onValueChange = listViewModel::onSearchTextChange
            )
            AddIconButtonComponent { singleViewModel.onOpenDialogClicked() }
        }
        Spacer(modifier = Modifier.width(width = 8.dp))
        SearchMultiCheckList(
            searchedText = searchText.text,
            emptyListTextResId = emptyListTextResId,
            items = items,
            onChecked = { listViewModel.observeCheckedListItems() }
        )
    }
}

@Composable
fun SearchMultiCheckList(
    searchedText: String = "",
    items: List<ListItemModel>,
    @StringRes emptyListTextResId: Int,
    onChecked: (Boolean) -> Unit,
    onClick: (ListItemModel) -> Unit = {}
) {
    Timber.tag(TAG).d("SearchMultiCheckList(...) called: size = %d", items.size)
    if (items.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = items.filter { it.selected }
                .getOrNull(0)?.let { items.indexOf(it) } ?: 0)
        var filteredItems: List<ListItemModel>
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            filteredItems = if (searchedText.isEmpty()) {
                items
            } else {
                items.filter { it.doesMatchSearchQuery(searchedText) }
            }
            itemsIndexed(filteredItems, key = { _, item -> item.itemId!! }) { _, item ->
                CheckedListItemComponent(
                    item = item,
                    //selected = item.selected,
                    onChecked = onChecked,
                    onClick = { onClick(item) }
                )
            }
        }
    } else {
        EmptyListTextComponent(emptyListTextResId)
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewSearchMultiCheckViewComponent() {
    HomeComposableTheme {
        Surface {
            /*SearchMultiCheckViewComponent(
                localityViewModel = SearchMultiCheckViewComponentModelImpl.previewModel(LocalContext.current),
                regionsListViewModel = RegionsListViewModelImpl.previewModel(LocalContext.current),
                regionViewModel = RegionViewModelImpl.previewModel(LocalContext.current),
                regionDistrictsListViewModel = RegionDistrictsListViewModelImpl.previewModel(
                    LocalContext.current
                ),
                regionDistrictViewModel = RegionDistrictViewModelImpl.previewModel(LocalContext.current)
            )*/
        }
    }
}
