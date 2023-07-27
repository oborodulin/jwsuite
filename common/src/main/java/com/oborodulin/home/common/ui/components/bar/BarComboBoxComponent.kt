package com.oborodulin.home.common.ui.components.bar

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.components.dialog.SearchSingleSelectDialog
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.home.common.util.OnTextFieldValueChange
import timber.log.Timber

private const val TAG = "Common.ui.BarComboBoxComponent"

@Composable
fun <T : List<*>, A : UiAction, E : UiSingleEvent> BarComboBoxComponent(
    modifier: Modifier,
    listViewModel: MviViewModeled<T, A, E>,
    loadListUiAction: A,
    inputWrapper: InputListItemWrapper,
    searchedItem: String = "",
    enabled: Boolean = true,
    @StringRes placeholderResId: Int,
    @StringRes listTitleResId: Int,
    leadingIcon: @Composable (() -> Unit)? = null,
    isShowListDialog: Boolean,
    onShowListDialog: () -> Unit,
    onDismissListDialog: () -> Unit,
    onShowSingleDialog: () -> Unit,
    maxLines: Int = Int.MAX_VALUE,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("BarComboBoxComponent(...) called")
    var itemId by rememberSaveable { mutableStateOf(inputWrapper.item.itemId) }
    var fieldValue by rememberSaveable {
        mutableStateOf(
            TextFieldValue(inputWrapper.item.headline, TextRange(inputWrapper.item.headline.length))
        )
    }
    val onFieldValueChange: OnTextFieldValueChange = {
        fieldValue = it
        onValueChange(ListItemModel(itemId, it.text))
    }
    // make sure to keep the value updated
    onFieldValueChange(fieldValue.copy(text = inputWrapper.item.headline))

    Timber.tag(TAG).d(
        "itemId = %s; fieldValue.text = %s; inputWrapper.item.headline = %s",
        itemId,
        fieldValue.text,
        inputWrapper.item.headline
    )
    /*    if (fieldValue.text != inputWrapper.item.headline) fieldValue =
            TextFieldValue(inputWrapper.item.headline, TextRange(inputWrapper.item.headline.length))
        Timber.tag(TAG).d(
            "BarComboBoxComponent(...): fieldValue = %s; inputWrapper = %s",
            fieldValue,
            inputWrapper
        )*/
    SearchSingleSelectDialog(
        isShow = isShowListDialog,
        title = stringResource(listTitleResId),
        searchedItem = searchedItem,
        viewModel = listViewModel,
        loadUiAction = loadListUiAction,
        onDismissRequest = onDismissListDialog,
        onAddButtonClick = onShowSingleDialog
    ) { item ->
        itemId = item.itemId
        onFieldValueChange(TextFieldValue(item.headline, TextRange(item.headline.length)))
        onValueChange(ListItemModel(item.itemId, item.headline))
    }
    // text field
    BarTextFieldComponent(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { if (enabled) onShowListDialog() },
        enabled = false,
        readOnly = true,
        fieldValue = fieldValue,
        isError = inputWrapper.errorId != null,
        placeholderResId = placeholderResId,
        leadingIcon = leadingIcon,
        trailingIcon = {
            if (enabled) {
                if (fieldValue.text.isEmpty()) {
                    Icon(
                        Icons.Outlined.ArrowDropDown,
                        contentDescription = "",
                        modifier = Modifier.offset(x = 10.dp)
                    )
                } else {
                    Icon(
                        Icons.Outlined.Clear,
                        contentDescription = "",
                        modifier = Modifier
                            .offset(x = 10.dp)
                            .clickable {
                                //just send an update that the field is now empty
                                onValueChange(ListItemModel())
                            }
                    )
                }
            }
        },
        keyboardActions = remember { KeyboardActions(onAny = { onImeKeyAction() }) },
        onValueChange = onFieldValueChange,
        colors = if (enabled) TextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            //For Icons
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
        ) else null
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewBarComboBoxComponent() {
    val isShowNewRegionDialog = remember { mutableStateOf(false) }
    HomeComposableTheme {
        Surface {
            /* BarComboBoxComponent(modifier = Modifier
                 .fillMaxWidth()
                 .height(60.dp),
                 listViewModel =,
                 loadListUiAction =,
                 inputWrapper = InputListItemWrapper(
                     item = ListItemModel(
                         itemId = UUID.randomUUID(),
                         headline = stringResource(R.string.preview_blank_text_field_val)
                     ),
                     errorId = R.string.preview_blank_text_field_err
                 ),
                 labelResId = R.string.preview_blank_text_field_lbl,
                 listTitleResId = R.string.preview_blank_text_field_lbl,
                 onValueChange = {},
                 onImeKeyAction = {})

             */
        }
    }
}
