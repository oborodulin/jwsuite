package com.oborodulin.home.common.ui.components.field

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.dialog.SearchSingleSelectDialog
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnValueChange
import timber.log.Timber
import java.util.UUID

private const val TAG = "Common.ui.ComboBoxComponent"

@Composable
fun <T : List<*>, A : UiAction> ComboBoxComponent(
    modifier: Modifier,
    listViewModel: MviViewModeled<T, A>,
    inputWrapper: InputListItemWrapper,
    @StringRes labelResId: Int,
    @StringRes listTitleResId: Int,
    leadingIcon: @Composable (() -> Unit)? = null,
    isShowItemDialog: MutableState<Boolean>,
    maxLines: Int = Int.MAX_VALUE,
    onValueChange: OnValueChange,
    onImeKeyAction: OnImeKeyAction,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    Timber.tag(TAG).d("ComboBoxComponent(...) called")
    var fieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                inputWrapper.item.headline, TextRange(inputWrapper.item.headline.length)
            )
        )
    }
    Timber.tag(TAG).d(
        "ComboBoxComponent(...): fieldValue.text = %s; inputWrapper.item.headline = %s",
        fieldValue.text,
        inputWrapper.item.headline
    )
    if (fieldValue.text != inputWrapper.item.headline) fieldValue =
        TextFieldValue(inputWrapper.item.headline, TextRange(inputWrapper.item.headline.length))
    Timber.tag(TAG).d(
        "ComboBoxComponent(...): fieldValue = %s; inputWrapper = %s",
        fieldValue,
        inputWrapper
    )
    val isShowListDialog = remember { mutableStateOf(false) }
    SearchSingleSelectDialog(
        isShow = isShowListDialog,
        title = stringResource(listTitleResId),
        viewModel = listViewModel,
        onAddButtonClick = { isShowItemDialog.value = true }
    ) { item -> fieldValue = TextFieldValue(item.headline, TextRange(item.headline.length)) }

    Column {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .clickable { isShowListDialog.value = true },
            enabled = false,
            readOnly = false,
            value = fieldValue,
            onValueChange = {
                fieldValue = it
                onValueChange(it.text)
            },
            label = { Text(stringResource(labelResId)) },
            leadingIcon = leadingIcon,
            trailingIcon = {
                if (fieldValue.text.isEmpty()) {
                    Icon(
                        Icons.Outlined.List,
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
                                onValueChange("")
                            }
                    )
                }
            },
            maxLines = maxLines,
            isError = inputWrapper.errorId != null,
            keyboardActions = remember {
                KeyboardActions(onAny = { onImeKeyAction() })
            },
            colors = colors
        )
        val errorMessage =
            if (inputWrapper.errorId != null) stringResource(inputWrapper.errorId) else inputWrapper.errorMsg
        errorMessage?.let {
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
    HomeComposableTheme {
        Surface {
           /* ComboBoxComponent(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
                inputWrapper = InputListItemWrapper(
                    item = ListItemModel(
                        itemId = UUID.randomUUID(),
                        headline = stringResource(R.string.preview_blank_text_field_val)
                    ),
                    errorId = R.string.preview_blank_text_field_err
                ),
                labelResId = R.string.preview_blank_text_field_lbl,
                onValueChange = {},
                onImeKeyAction = {})*/
        }
    }
}
