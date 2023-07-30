package com.oborodulin.home.common.ui.components.field

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.oborodulin.home.common.util.OnValueChange
import timber.log.Timber

private const val TAG = "Common.ui.TextFieldComponent"

@Composable
fun TextFieldComponent(
    modifier: Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    inputWrapper: InputWrapper,
    @StringRes labelResId: Int? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = remember { KeyboardOptions.Default },
    visualTransformation: VisualTransformation = remember { VisualTransformation.None },
    onValueChange: OnValueChange = {},
    onImeKeyAction: OnImeKeyAction,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    Timber.tag(TAG).d("TextFieldComponent(...) called")
    var fieldValue by remember {
        mutableStateOf(TextFieldValue(inputWrapper.value, TextRange(inputWrapper.value.length)))
    }
    fieldValue = fieldValue.copy(text = inputWrapper.value) // make sure to keep the value updated
    Timber.tag(TAG).d(
        "TextFieldComponent(...): fieldValue.text = %s; inputWrapper.value = %s",
        fieldValue.text,
        inputWrapper.value
    )
    /*    if (fieldValue.text != inputWrapper.value) fieldValue =
            TextFieldValue(inputWrapper.value, TextRange(inputWrapper.value.length))
        Timber.tag(TAG).d(
            "TextFieldComponent(...): fieldValue = %s; inputWrapper = %s",
            fieldValue,
            inputWrapper
        )*/
    Column {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp),//.weight(1f),
            enabled = enabled,
            readOnly = readOnly,
            value = fieldValue,
            onValueChange = {
                fieldValue = it
                onValueChange(it.text)
            },
            label = labelResId?.let { { Text(stringResource(it)) } },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
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
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTextFieldComponent() {
    HomeComposableTheme {
        Surface {
            TextFieldComponent(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
                inputWrapper = InputWrapper(
                    value = stringResource(R.string.preview_blank_text_field_val),
                    errorId = R.string.preview_blank_text_field_err
                ),
                labelResId = R.string.preview_blank_text_field_lbl,
                onValueChange = {},
                onImeKeyAction = {})
        }
    }
}
