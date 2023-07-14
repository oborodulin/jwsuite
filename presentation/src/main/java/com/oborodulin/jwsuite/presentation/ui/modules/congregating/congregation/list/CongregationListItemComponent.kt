package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.items.ListItemComponent
import com.oborodulin.home.common.ui.theme.Typography
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme

/**
 * Created by tfakioglu on 12.December.2021
 */
private const val TAG = "Congregating.ui.CongregationListItemComponent"
private val EMPTY: OnListItemEvent = {}

@Composable
fun CongregationListItemComponent(
    @DrawableRes iconResId: Int? = null,
    item: CongregationsListItem,
    selected: Boolean = false,
    itemActions: List<ComponentUiAction> = emptyList(),
    background: Color = Color.Transparent,
    onFavorite: OnListItemEvent,
    onClick: OnListItemEvent
) {
    ListItemComponent(
        iconResId = iconResId,
        item = item,
        selected = selected,
        itemActions = itemActions,
        background = background,
        onClick = onClick
    ) {
        Row(
            Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(2f)
            ) {
                Row {
                    if (onFavorite !== EMPTY) {
                        Image(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .padding(4.dp)
                                .clickable {
                                    if (!item.isFavorite) {
                                        onFavorite(item)
                                    }
                                },
                            painter = when (item.isFavorite) {
                                true -> painterResource(R.drawable.outline_favorite_black_20)
                                false -> painterResource(R.drawable.outline_favorite_border_black_20)
                            },
                            contentDescription = ""
                        )
                    }
                    Text(
                        text = item.headline,
                        style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        maxLines = 2
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
fun PreviewCongregationListItemComponent() {
    JWSuiteTheme {
        Surface {
            CongregationListItemComponent(
                //iconResId = R.drawable.outline_photo_24,
                item = CongregationsListViewModelImpl.previewList(LocalContext.current).first(),
                itemActions = listOf(
                    ComponentUiAction.EditListItem { println() },
                    ComponentUiAction.DeleteListItem { println() }),
                onFavorite = { println() },
                onClick = { println() }
            )
        }
    }
}