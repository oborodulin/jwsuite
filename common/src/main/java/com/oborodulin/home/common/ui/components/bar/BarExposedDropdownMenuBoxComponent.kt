package com.oborodulin.home.common.ui.components.bar

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    inputWrapper: InputWrapper,             // enum.name
    resourceItems: List<String> = listOf(), // resources
    listItems: List<String> = listOf(),     // Enum.names
    @StringRes placeholderResId: Int? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = remember { KeyboardOptions.Default },
    visualTransformation: VisualTransformation = remember { VisualTransformation.None },
    onValueChange: OnValueChange,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("BarExposedDropdownMenuBoxComponent(...) called")
    val resValue =
        if (resourceItems.isNotEmpty()) resourceItems[listItems.indexOf(inputWrapper.value)] else inputWrapper.value // resource
    var fieldValue by rememberSaveable { mutableStateOf(resValue) } // resource
    var expanded by rememberSaveable { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }
    val textStyle = LocalTextStyle.current
    // make sure there is no background color in the decoration box
    val colors = TextFieldDefaults.colors(
        focusedContainerColor = Color.Unspecified,
        unfocusedContainerColor = Color.Unspecified,
        disabledContainerColor = Color.Unspecified,
    )

    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        MaterialTheme.colorScheme.onSurface
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor, lineHeight = 50.sp))

    // request focus when this composable is first initialized
    val focusRequester = FocusRequester()
    SideEffect {
        focusRequester.requestFocus()
    }

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
            readOnly = true,
            inputWrapper = inputWrapper,
            placeholderResId = placeholderResId,
            leadingIcon = leadingIcon,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            //onValueChange = onValueChange
        )
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
                        fieldValue = resOption
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
