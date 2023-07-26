package com.oborodulin.home.common.ui.components.bar

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.OnImeKeyAction
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
    resourceItems: List<String> = listOf(), // resources
    listItems: List<String> = listOf(), // Enum.names
    @StringRes labelResId: Int? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = remember {
        KeyboardOptions.Default
    },
    visualTransformation: VisualTransformation = remember {
        VisualTransformation.None
    },
    onValueChange: OnValueChange,
    onImeKeyAction: OnImeKeyAction,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    Timber.tag(TAG).d("BarExposedDropdownMenuBoxComponent(...) called")
    val resValue =
        if (resourceItems.isNotEmpty()) resourceItems[listItems.indexOf(inputWrapper.value)] else inputWrapper.value // resource
    val fieldValue = remember { mutableStateOf(resValue) } // resource
    var expanded by remember { mutableStateOf(false) }
    // the box
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        // text field
        BarTextFieldComponent( modifier = modifier
            .fillMaxWidth()
            .menuAnchor())
        Column {
            OutlinedTextField(
                enabled = enabled,
                readOnly = true,
                value = fieldValue.value, // resource
                onValueChange = {
                    fieldValue.value = it // resource
                    //onValueChange(it)
                },
                label = { labelResId?.let { Text(stringResource(it)) } },
                leadingIcon = leadingIcon,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                maxLines = maxLines,
                isError = inputWrapper.errorId != null,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = remember {
                    KeyboardActions(onAny = { onImeKeyAction() })
                },
                colors = colors
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
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // listItems: Enum.names
            listItems.forEach { selectedOption -> // Enum.name
                // menu item: Enums to resources
                val resOption =
                    if (resourceItems.isNotEmpty()) resourceItems[listItems.indexOf(selectedOption)] else selectedOption // resource
                Timber.tag(TAG).d("selectedOption = %s; resOption = %s", selectedOption, resOption)
                DropdownMenuItem(text = { Text(text = resOption) },
                    onClick = {
                        fieldValue.value = resOption
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
                labelResId = R.string.preview_blank_text_field_lbl,
                onValueChange = {},
                onImeKeyAction = {}
            )
        }
    }
}
