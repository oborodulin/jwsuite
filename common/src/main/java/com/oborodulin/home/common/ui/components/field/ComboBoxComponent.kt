package com.oborodulin.home.common.ui.components.field

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.IconComponent
import com.oborodulin.home.common.ui.components.dialog.SearchSingleSelectDialog
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.LogLevel.LOG_UI_COMPONENTS
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnListItemEvent
import timber.log.Timber

private const val TAG = "Common.ui.ComboBoxComponent"

@Composable
fun <T : ListItemModel, L : List<T>, A : UiAction, E : UiSingleEvent> ComboBoxComponent(
    modifier: Modifier,
    listViewModel: MviViewModeled<L, A, E>,
    loadListUiAction: A,
    inputWrapper: InputListItemWrapper<T>,
    enabled: Boolean = true,
    @StringRes labelResId: Int,
    @StringRes listTitleResId: Int,
    leadingImageVector: ImageVector? = null,
    @DrawableRes leadingPainterResId: Int? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    @StringRes leadingCntDescResId: Int? = null,
    isShowListDialog: Boolean,
    onShowListDialog: () -> Unit,
    onDismissListDialog: () -> Unit,
    onShowSingleDialog: () -> Unit,
    maxLines: Int = Int.MAX_VALUE,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction
) {
    if (LOG_UI_COMPONENTS) {
        Timber.tag(TAG).d("ComboBoxComponent(...) called")
    }
    //val enabled by remember { mutableStateOf(enabled) }
    var itemId by remember { mutableStateOf(inputWrapper.item?.itemId) }
    var supportingText by remember { mutableStateOf(inputWrapper.item?.supportingText) }
    var fieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                inputWrapper.item?.headline.orEmpty(),
                TextRange(inputWrapper.item?.headline.orEmpty().length)
            )
        )
    }
    fieldValue =
        fieldValue.copy(text = inputWrapper.item?.headline.orEmpty()) // make sure to keep the value updated
    if (LOG_UI_COMPONENTS) {
        Timber.tag(TAG).d(
            "itemId = %s; fieldValue.text = %s; inputWrapper.item.headline = %s",
            itemId,
            fieldValue.text,
            inputWrapper.item?.headline
        )
    }
    /*    if (fieldValue.text != inputWrapper.item?.headline.orEmpty()) fieldValue =
            TextFieldValue(inputWrapper.item?.headline.orEmpty(), TextRange(inputWrapper.item?.headline.orEmpty().length))
        if (LOG_UI_COMPONENTS) {Timber.tag(TAG).d(
            "ComboBoxComponent: fieldValue = %s; inputWrapper = %s",
            fieldValue,
            inputWrapper
        )}*/
    SearchSingleSelectDialog(
        isShow = isShowListDialog,
        title = stringResource(listTitleResId),
        viewModel = listViewModel,
        loadUiAction = loadListUiAction,
        onDismissRequest = onDismissListDialog,
        onAddButtonClick = onShowSingleDialog
    ) { item ->
        if (enabled) {
            itemId = item.itemId
            supportingText = item.supportingText
            fieldValue = TextFieldValue(item.headline, TextRange(item.headline.length))
            onValueChange(ListItemModel(item.itemId, item.headline, item.supportingText))
        }
    }
    Column {
        // https://stackoverflow.com/questions/67902919/jetpack-compose-textfield-clickable-does-not-work
        // https://github.com/JetBrains/compose-multiplatform/issues/220
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .clickable { if (enabled) onShowListDialog() },
            enabled = false,
            readOnly = true,
            value = fieldValue,
            onValueChange = {
                if (enabled) {
                    fieldValue = it
                    onValueChange(ListItemModel(itemId, it.text, supportingText))
                }
            },
            label = { Text(stringResource(labelResId)) },
            leadingIcon = {
                IconComponent(
                    icon = leadingIcon,
                    imageVector = leadingImageVector,
                    painterResId = leadingPainterResId,
                    contentDescriptionResId = leadingCntDescResId,
                    size = 36.dp
                )
            },
            trailingIcon = {
                if (enabled) {
                    if (fieldValue.text.isEmpty()) {
                        Icon(
                            Icons.Outlined.ArrowDropDown,
                            contentDescription = stringResource(R.string.cmb_drop_down_cnt_desc),
                            modifier = Modifier.offset(x = 10.dp)
                        )
                    } else {
                        Icon(
                            Icons.Outlined.Clear,
                            contentDescription = stringResource(R.string.cmb_clear_cnt_desc),
                            modifier = Modifier
                                .offset(x = 10.dp)
                                .clickable {
                                    //just send an update that the field is now empty
                                    if (enabled) onValueChange(ListItemModel())
                                }
                        )
                    }
                }
            },
            maxLines = maxLines,
            isError = inputWrapper.errorId != null,
            keyboardActions = remember { KeyboardActions(onAny = { onImeKeyAction() }) },
            colors = if (enabled) OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                //For Icons
                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
            ) else OutlinedTextFieldDefaults.colors()
        )
        inputWrapper.errorMessage(LocalContext.current)?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewComboBoxComponent() {
    val isShowNewRegionDialog = rememberSaveable { mutableStateOf(false) }
    HomeComposableTheme {
        Surface {
            /* ComboBoxComponent(modifier = Modifier
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
