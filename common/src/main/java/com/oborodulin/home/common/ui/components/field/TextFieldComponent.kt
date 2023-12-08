package com.oborodulin.home.common.ui.components.field

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.IconComponent
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.LogLevel.LOG_UI_COMPONENTS
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnValueChange
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Common.ui.TextFieldComponent"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TextFieldComponent(
    modifier: Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    inputWrapper: InputWrapper,
    maxLength: Int = Int.MAX_VALUE,
    @StringRes placeholderResId: Int? = null,
    @StringRes labelResId: Int? = null,
    @StringRes helperResId: Int? = null,
    leadingImageVector: ImageVector? = null,
    @DrawableRes leadingPainterResId: Int? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    @StringRes leadingCntDescResId: Int? = null,
    trailingImageVector: ImageVector? = null,
    @DrawableRes trailingPainterResId: Int? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    @StringRes trailingCntDescResId: Int? = null,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = remember { KeyboardOptions.Default },
    visualTransformation: VisualTransformation = remember { VisualTransformation.None },
    onValueChange: OnValueChange = {},
    onImeKeyAction: OnImeKeyAction,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    if (LOG_UI_COMPONENTS) Timber.tag(TAG).d("TextFieldComponent(...) called")
    var fieldValue by remember {
        mutableStateOf(TextFieldValue(inputWrapper.value, TextRange(inputWrapper.value.length)))
    }
    fieldValue = fieldValue.copy(text = inputWrapper.value) // make sure to keep the value updated
    if (LOG_UI_COMPONENTS) Timber.tag(TAG).d(
        "TextFieldComponent: fieldValue.text = %s; inputWrapper.value = %s",
        fieldValue.text,
        inputWrapper.value
    )
    // https://stackoverflow.com/questions/69036917/text-field-text-goes-below-the-ime-in-lazycolum-jetpack-compose/69120348#69120348
    val relocation = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()
    /*    if (fieldValue.text != inputWrapper.value) fieldValue =
            TextFieldValue(inputWrapper.value, TextRange(inputWrapper.value.length))
        if (LOG_UI_COMPONENTS) Timber.tag(TAG).d(
            "TextFieldComponent: fieldValue = %s; inputWrapper = %s",
            fieldValue,
            inputWrapper
        )*/
    Column {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .bringIntoViewRequester(relocation)
                .onFocusEvent {
                    if (it.isFocused) scope.launch { delay(300); relocation.bringIntoView() }
                },//.weight(1f),
            enabled = enabled,
            readOnly = readOnly,
            value = fieldValue,
            onValueChange = {
                // https://stackoverflow.com/questions/67136058/textfield-maxlength-android-jetpack-compose
                if (it.text.length <= maxLength) {
                    fieldValue = it; onValueChange(it.text)
                }
            },
            label = labelResId?.let { { Text(stringResource(it)) } },
            placeholder = placeholderResId?.let {
                { Text(text = stringResource(it), maxLines = 1, overflow = TextOverflow.Ellipsis) }
            },
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
                IconComponent(
                    icon = trailingIcon,
                    imageVector = trailingImageVector,
                    painterResId = trailingPainterResId,
                    contentDescriptionResId = trailingCntDescResId
                )
            },
            supportingText = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    helperResId?.let {
                        Text(
                            modifier = Modifier.weight(8f),
                            text = stringResource(it),
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (maxLength != Int.MAX_VALUE) {
                        Text(
                            modifier = Modifier.weight(2f),
                            text = "${fieldValue.text.length} / $maxLength",
                            textAlign = TextAlign.End
                        )
                    }
                }
            },
            maxLines = maxLines,
            isError = inputWrapper.errorId != null,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = remember { KeyboardActions(onAny = { onImeKeyAction() }) },
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
                leadingPainterResId = R.drawable.outline_photo_black_24,
                placeholderResId = R.string.preview_blank_text_field_ph,
                labelResId = R.string.preview_blank_text_field_lbl,
                helperResId = R.string.preview_blank_text_field_hlp,
                onValueChange = {},
                onImeKeyAction = {}
            )
        }
    }
}
