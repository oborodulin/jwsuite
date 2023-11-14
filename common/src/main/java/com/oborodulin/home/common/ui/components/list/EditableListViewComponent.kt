package com.oborodulin.home.common.ui.components.list

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.list.items.ListItemComponent
import com.oborodulin.home.common.ui.model.ListItemModel
import timber.log.Timber

private const val TAG = "Common.ui.EditableListViewComponent"

@Composable
fun EditableListViewComponent(
    items: List<ListItemModel>,
    searchedText: String = "",
    @StringRes dlgConfirmDelResId: Int,
    @StringRes emptyListResId: Int,
    isEmptyListTextOutput: Boolean = true,
    onEdit: (ListItemModel) -> Unit,
    onDelete: (ListItemModel) -> Unit,
    onClick: (ListItemModel) -> Unit
) {
    Timber.tag(TAG).d("EditableListViewComponent(...) called: size = %d", items.size)
    if (items.isNotEmpty()) {
        // https://developer.android.com/jetpack/compose/performance/bestpractices
        val filteredItems = remember(items, searchedText) {
            if (searchedText.isEmpty()) {
                items
            } else {
                items.filter { it.doesMatchSearchQuery(searchedText) }
            }
        }
        val firstVisibleItem = filteredItems.filter { it.selected }.getOrNull(0)
        LaunchedEffect(firstVisibleItem) {
            Timber.tag(TAG)
                .d("EditableListViewComponent -> LaunchedEffect() BEFORE collect ui state flow")
            firstVisibleItem?.let { onClick(it) }
        }
        val listState = rememberLazyListState(initialFirstVisibleItemIndex = firstVisibleItem?.let {
            filteredItems.indexOf(it)
        } ?: 0)
        LazyColumn(
            state = listState, //rememberLazyListState(),
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            //items(regionDistricts.size) { index ->
            //    regionDistricts[index].let { regionDistrict ->
            //        val isSelected = (selectedIndex == index)
            itemsIndexed(filteredItems, key = { _, item -> item.itemId!! }) { _, item ->
                ListItemComponent(
                    item = item,
                    itemActions = listOf(
                        ComponentUiAction.EditListItem { onEdit(item) },
                        ComponentUiAction.DeleteListItem(
                            stringResource(dlgConfirmDelResId, item.headline)
                        ) { onDelete(item) }),
                    selected = item.selected,  //isSelected,
                    //background = (if (isSelected) Color.LightGray else Color.Transparent),
                    onClick = { onClick(item) } //if (selectedIndex != index) selectedIndex = index
                )
            }
        }
    } else {
        if (isEmptyListTextOutput) {
            EmptyListTextComponent(emptyListResId)
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewEditableListViewComponent() {
    /*JWSuiteTheme {
        Surface {
            EditableListViewComponent(
                items = EditableListViewComponentViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )
        }
    }*/
}
