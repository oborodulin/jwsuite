package com.oborodulin.home.common.ui.components.list.items

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Top
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.dialog.alert.DeleteConfirmDialogComponent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.ui.theme.Typography
import com.oborodulin.home.common.util.Constants.EMPTY_LIST_ITEM_EVENT
import com.oborodulin.home.common.util.OnListItemEvent
import timber.log.Timber
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Common.ui"

// https://m3.material.io/components/lists/specs
@Composable
fun ListItemComponent(
    @DrawableRes iconResId: Int? = null,
    item: ListItemModel,
    selected: Boolean = false,
    background: Color = Color.Transparent,
    itemActions: List<ComponentUiAction> = emptyList(),
    onClick: OnListItemEvent = EMPTY_LIST_ITEM_EVENT,
    content: @Composable (() -> Unit)? = null
) {
    Timber.tag(TAG)
        .d(
            "ListItemComponent(...) called: {\"listItem\": {\"icon\": %s, \"itemId\": \"%s\", \"headline\": \"%s\", \"supportingText\": \"%s\", \"value\": \"%s\"}}",
            iconResId,
            item.itemId,
            item.headline,
            item.supportingText,
            item.value
        )
    // https://m3.material.io/components/lists/specs
    // https://stackoverflow.com/questions/69707827/jetpack-compose-find-how-many-lines-a-text-will-take-before-composition
    val cardHeight = when {
        itemActions.size <= 1 && item.supportingText.isNullOrEmpty() -> 56.dp
        itemActions.size <= 1 && (!item.supportingText.isNullOrEmpty() && !item.supportingText.contains(
            "\n"
        )) -> 72.dp

        else -> 88.dp
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .clip(RoundedCornerShape(8.dp))
            .background(if (selected) Color.LightGray else Color.Transparent)
            .selectable(
                selected = selected,
                onClick = { if (onClick !== EMPTY_LIST_ITEM_EVENT) onClick(item) })
            .padding(horizontal = 4.dp, vertical = 4.dp),
        //.background(color = MaterialTheme.colorScheme.background)
        //.clickable {}
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        val widthColIcon = 0.6f
        val widthColContent = 2.9f + (if (iconResId == null) widthColIcon / 2.0f else 0f)
        val widthColActions = 0.3f + (if (iconResId == null) widthColIcon / 2.0f else 0f)
        Row(
            Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            iconResId?.let {
                Column(
                    Modifier
                        .weight(widthColIcon),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .padding(start = 16.dp, end = 8.dp),
                        painter = painterResource(it),
                        contentScale = ContentScale.Fit,
                        contentDescription = ""
                    )
                }
            }
            Column(
                verticalArrangement = Top,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(widthColContent)
                    .padding(horizontal = 4.dp)
            ) {
                if (content != null) {
                    content()
                } else {
                    Row(Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.weight(2f)) {
                            Text(
                                text = item.headline,
                                style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                maxLines = 2
                            )
                            item.supportingText?.let {
                                Text(
                                    modifier = Modifier.padding(top = 4.dp),
                                    text = it,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        item.value?.let {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                //val nf = NumberFormat.getCurrencyInstance(Locale.getDefault())
                                val nf = NumberFormat.getNumberInstance(Locale.getDefault())
                                nf.roundingMode = RoundingMode.HALF_UP
                                nf.maximumFractionDigits = 0
                                Text(
                                    text = nf.format(it),
                                    style = Typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold, fontSize = 20.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(widthColActions),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                val isShowAlert = rememberSaveable { mutableStateOf(false) }
                val spaceVal = 18
                var itemIndex = 0
                for (action in itemActions.filter { it.isMenuButton }) {
                    when (action) {
                        is ComponentUiAction.EditListItem -> {
                            Image(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { action.event(item) },
                                painter = painterResource(R.drawable.outline_mode_edit_black_24),
                                contentDescription = ""
                            )
                        }

                        is ComponentUiAction.DeleteListItem -> {
                            DeleteConfirmDialogComponent(
                                isShow = isShowAlert, text = action.alertText
                            ) { action.event(item) }
                            Image(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { isShowAlert.value = true },
                                painter = painterResource(R.drawable.outline_delete_black_24),
                                contentDescription = ""
                            )
                        }

                        is ComponentUiAction.PayListItem -> {
                        }
                    }
                    itemIndex++
                    if (itemIndex < itemActions.size) Spacer(Modifier.height(spaceVal.dp))
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewListItemComponent() {
    HomeComposableTheme {
        Surface {
            Column {
                ListItemComponent(
                    iconResId = R.drawable.outline_photo_24,
                    item = ListItemModel.defaultListItemModel(LocalContext.current),
                    itemActions = listOf(
                        ComponentUiAction.EditListItem { println() },
                        ComponentUiAction.DeleteListItem { println() }),
                    onClick = { println() }
                ) {
                    Text(
                        text = stringResource(R.string.preview_list_item_text),
                        style = Typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
                ListItemComponent(
                    iconResId = R.drawable.outline_photo_24,
                    item = ListItemModel.defaultListItemModel(LocalContext.current),
                    itemActions = listOf(
                        ComponentUiAction.EditListItem { println() },
                        ComponentUiAction.DeleteListItem { println() }),
                    onClick = { println() }
                )
                ListItemComponent(
                    item = ListItemModel.defaultListItemModel(LocalContext.current),
                    itemActions = listOf(
                        ComponentUiAction.EditListItem { println() },
                        ComponentUiAction.DeleteListItem { println() }),
                    onClick = { println() }
                )
            }
        }
    }
}
