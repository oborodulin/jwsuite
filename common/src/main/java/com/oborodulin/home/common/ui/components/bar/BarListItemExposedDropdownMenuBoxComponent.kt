package com.oborodulin.home.common.ui.components.bar

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.oborodulin.home.common.ui.components.IconComponent
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnTextFieldValueChange
import timber.log.Timber

private const val TAG = "Common.ui.BarListItemExposedDropdownMenuBoxComponent"

// https://alexzh.com/jetpack-compose-dropdownmenu/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : ListItemModel> BarListItemExposedDropdownMenuBoxComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    inputWrapper: InputListItemWrapper<T>,
    items: List<T> = listOf(),
    @StringRes placeholderResId: Int? = null,
    leadingImageVector: ImageVector? = null,
    @DrawableRes leadingPainterResId: Int? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    @StringRes leadingCntDescResId: Int? = null,
    keyboardOptions: KeyboardOptions = remember { KeyboardOptions.Default },
    visualTransformation: VisualTransformation = remember { VisualTransformation.None },
    onValueChange: (T) -> Unit,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("BarListItemExposedDropdownMenuBoxComponent(...) called")
    // set the correct cursor position when this composable is first initialized
    var fieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                inputWrapper.item?.headline.orEmpty(),
                TextRange(inputWrapper.item?.headline.orEmpty().length)
            )
        )
    }
    val onFieldValueChange: OnTextFieldValueChange = { fieldValue = it }
    // make sure to keep the value updated
    onFieldValueChange(fieldValue.copy(text = inputWrapper.item?.headline.orEmpty()))
    Timber.tag(TAG).d(
        "fieldValue.text = %s; inputWrapper.item.headline = %s",
        fieldValue.text,
        inputWrapper.item?.headline
    )
    var expanded by rememberSaveable { mutableStateOf(false) }

    // the box
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // text field
        BarTextFieldComponent(
            modifier = modifier.menuAnchor(),
            enabled = enabled,
            readOnly = true,
            fieldValue = fieldValue,
            isError = inputWrapper.errorId != null,
            placeholderResId = placeholderResId,
            leadingIcon = {
                IconComponent(
                    icon = leadingIcon,
                    imageVector = leadingImageVector,
                    painterResId = leadingPainterResId,
                    contentDescriptionResId = leadingCntDescResId
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = remember { KeyboardActions(onAny = { onImeKeyAction() }) },
            onValueChange = onFieldValueChange
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { option ->
                Timber.tag(TAG).d("option = %s", option)
                DropdownMenuItem(text = { Text(text = option.headline) },
                    onClick = {
                        expanded = false
                        onFieldValueChange(
                            TextFieldValue(option.headline, TextRange(option.headline.length))
                        )
                        onValueChange(option)
                    }
                )
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewBarListItemExposedDropdownMenuBoxComponent() {
    HomeComposableTheme {
        Surface {
            /* BarListItemExposedDropdownMenuBoxComponent(modifier = Modifier
                 .fillMaxWidth()
                 .height(60.dp),
                 inputWrapper = InputWrapper(
                     value = stringResource(R.string.preview_blank_text_field_val),
                     errorId = R.string.preview_blank_text_field_err
                 ),
                 placeholderResId = R.string.preview_blank_text_field_lbl,
                 onValueChange = {},
                 onImeKeyAction = {}
             )*/
        }
    }
}
