package com.oborodulin.home.common.ui.components.list.items

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.ui.theme.Typography
import com.oborodulin.home.common.util.OnListItemEvent

/**
 * Created by tfakioglu on 12.December.2021
 */
private const val TAG = "Common.ui.CheckedListItemComponent"
private val _EMPTY: OnListItemEvent = {}

@Composable
fun CheckedListItemComponent(
    @DrawableRes iconResId: Int? = null,
    item: ListItemModel,
    selected: Boolean = false,
    itemActions: List<ComponentUiAction> = emptyList(),
    onChecked: (Boolean) -> Unit = {},
    onClick: OnListItemEvent
) {
    ListItemComponent(
        iconResId = iconResId,
        item = item,
        selected = selected,
        itemActions = itemActions,
        onClick = onClick
    ) {
        Row(Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(2f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = item.headline,
                        style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Start,
                        maxLines = 2
                    )
                    Checkbox(
                        modifier = Modifier
                            .padding(0.dp)
                            //.align(Alignment.CenterVertically),
                            .alignByBaseline(),
                        checked = item.checked,
                        onCheckedChange = { item.checked = it; onChecked(it) }
                    )
                }
                item.supportingText?.let {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = it,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewCheckedListItemComponent() {
    HomeComposableTheme {
        Surface {
            CheckedListItemComponent(
                //iconResId = R.drawable.outline_photo_24,
                item = ListItemModel.defaultListItemModel(LocalContext.current),
                itemActions = listOf(
                    ComponentUiAction.EditListItem { println() },
                    ComponentUiAction.DeleteListItem { println() }),
                onChecked = { println() },
                onClick = { println() }
            )
        }
    }
}