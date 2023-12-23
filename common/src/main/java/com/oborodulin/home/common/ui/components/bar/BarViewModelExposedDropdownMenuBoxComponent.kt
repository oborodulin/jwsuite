package com.oborodulin.home.common.ui.components.bar

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.LogLevel.LOG_UI_COMPONENTS
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.home.common.util.OnTextFieldValueChange
import timber.log.Timber

private const val TAG = "Common.ui.BarViewModelExposedDropdownMenuBoxComponent"

// https://alexzh.com/jetpack-compose-dropdownmenu/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : ListItemModel, L : List<*>, A : UiAction, E : UiSingleEvent> BarViewModelExposedDropdownMenuBoxComponent(
    modifier: Modifier,
    enabled: Boolean = true,
    listViewModel: MviViewModeled<L, A, E>,
    loadListUiAction: A,
    inputWrapper: InputListItemWrapper<T>,
    @StringRes placeholderResId: Int? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = remember { KeyboardOptions.Default },
    visualTransformation: VisualTransformation = remember { VisualTransformation.None },
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction
) {
    if (LOG_UI_COMPONENTS) Timber.tag(TAG)
        .d("BarViewModelExposedDropdownMenuBoxComponent(...) called")
    LaunchedEffect(Unit) {
        if (LOG_UI_COMPONENTS) Timber.tag(TAG)
            .d("BarViewModelExposedDropdownMenuBoxComponent -> LaunchedEffect() BEFORE collect ui state flow")
        listViewModel.submitAction(loadListUiAction)
    }
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
    if (LOG_UI_COMPONENTS) Timber.tag(TAG).d(
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
            leadingIcon = leadingIcon,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = remember {
                KeyboardActions(onAny = { onImeKeyAction() })
            },
            onValueChange = onFieldValueChange
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
                if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
                CommonScreen(state = state) { items ->
                    items as List<ListItemModel>
                    items.forEach { option ->
                        if (LOG_UI_COMPONENTS) Timber.tag(TAG).d("option = %s", option)
                        DropdownMenuItem(text = { Text(text = option.headline) },
                            onClick = {
                                expanded = false
                                onFieldValueChange(
                                    TextFieldValue(
                                        option.headline, TextRange(option.headline.length)
                                    )
                                )
                                onValueChange(ListItemModel(option.itemId, option.headline))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewBarViewModelExposedDropdownMenuBoxComponent() {
    HomeComposableTheme {
        Surface {
            /* BarViewModelExposedDropdownMenuBoxComponent(modifier = Modifier
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
