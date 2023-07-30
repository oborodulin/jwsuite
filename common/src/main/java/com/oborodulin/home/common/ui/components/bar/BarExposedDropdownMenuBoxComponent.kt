package com.oborodulin.home.common.ui.components.bar

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnTextFieldValueChange
import com.oborodulin.home.common.util.OnValueChange
import timber.log.Timber

private const val TAG = "Common.ui.BarExposedDropdownMenuBoxComponent"

// https://alexzh.com/jetpack-compose-dropdownmenu/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarExposedDropdownMenuBoxComponent(
    modifier: Modifier,
    enabled: Boolean = true,
    inputWrapper: InputWrapper,         // enum.name
    values: List<String> = listOf(),    // resources
    keys: List<String> = listOf(),      // Enum.names
    @StringRes placeholderResId: Int? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = remember { KeyboardOptions.Default },
    visualTransformation: VisualTransformation = remember { VisualTransformation.None },
    onValueChange: OnValueChange,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("BarExposedDropdownMenuBoxComponent(...) called")
    val value =
        if (values.isNotEmpty()) values[keys.indexOf(inputWrapper.value)] else inputWrapper.value // resource
    // set the correct cursor position when this composable is first initialized
    var fieldValue by remember {
        mutableStateOf(TextFieldValue(value, TextRange(value.length))) // resource
    }
    val onFieldValueChange: OnTextFieldValueChange = { fieldValue = it }
    // make sure to keep the value updated
    onFieldValueChange(fieldValue.copy(text = value))

    var expanded by rememberSaveable { mutableStateOf(false) }

    // the box
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        // text field
        BarTextFieldComponent(
            modifier = modifier.menuAnchor(),
            enabled = enabled,
            readOnly = true,
            fieldValue = fieldValue,
            isError = inputWrapper.errorId != null,
            placeholderResId = placeholderResId,
            leadingIcon = leadingIcon,
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
            keys.forEach { selectedOption -> // Enum.name
                // menu item: Enums to resources
                val option =
                    if (values.isNotEmpty()) values[keys.indexOf(selectedOption)] else selectedOption // resource
                Timber.tag(TAG)
                    .d("selectedOption = %s; option = %s", selectedOption, option)
                DropdownMenuItem(text = { Text(text = option) },
                    onClick = {
                        onFieldValueChange(TextFieldValue(option, TextRange(option.length)))
                        expanded = false
                        onValueChange(selectedOption) // Enum.name
                    })
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewBarExposedDropdownMenuBoxComponent() {
    HomeComposableTheme {
        Surface {
            BarExposedDropdownMenuBoxComponent(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
                inputWrapper = InputWrapper(
                    value = stringResource(R.string.preview_blank_text_field_val),
                    errorId = R.string.preview_blank_text_field_err
                ),
                placeholderResId = R.string.preview_blank_text_field_lbl,
                onValueChange = {},
                onImeKeyAction = {}
            )
        }
    }
}
