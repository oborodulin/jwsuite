package com.oborodulin.home.common.ui.components.list

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.util.Constants.EMPTY_LIST_ITEM_EVENT
import com.oborodulin.home.common.util.OnListItemEvent
import timber.log.Timber

private const val TAG = "Common.ui.ListViewComponent"

@Composable
fun ListViewComponent(
    items: List<ListItemModel>,
    @StringRes emptyListResId: Int,
    isEmptyListTextOutput: Boolean = true,
    onClick: OnListItemEvent = EMPTY_LIST_ITEM_EVENT
) {
    Timber.tag(TAG).d("ListViewComponent(...) called: size = %d", items.size)
    EditableListViewComponent(
        items = items,
        emptyListResId = emptyListResId,
        isEmptyListTextOutput = isEmptyListTextOutput,
        onClick = onClick
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewListViewComponent() {
    /*JWSuiteTheme {
        Surface {
            ListViewComponent(
                items = ListViewComponentViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )
        }
    }*/
}