package com.oborodulin.home.common.ui.components.field

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.util.Constants
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnValueChange
import com.oborodulin.home.common.util.toast
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private const val TAG = "Common.ui.DatePickerComponent"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    modifier: Modifier,
    inputWrapper: InputWrapper,
    @StringRes labelResId: Int? = null,
    @StringRes datePickerTitleResId: Int? = null,
    keyboardOptions: KeyboardOptions = remember { KeyboardOptions.Default },
    onValueChange: OnValueChange,
    onImeKeyAction: OnImeKeyAction,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    var isShowDialog by remember { mutableStateOf(false) }
    val onShowDialog = { isShowDialog = true }
    val onDismissRequest = { isShowDialog = false }

    var fieldValue by remember {
        mutableStateOf(TextFieldValue(inputWrapper.value, TextRange(inputWrapper.value.length)))
    }

    if (isShowDialog) {
        val datePickerTitlePadding = PaddingValues(
            start = 24.dp,
            end = 12.dp,
            top = 16.dp
        )
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = Instant.now().toEpochMilli()
        )
        DatePickerDialog(
            shape = RoundedCornerShape(6.dp),
            onDismissRequest = onDismissRequest,
            confirmButton = {
                onDismissRequest()
                // Seems broken at the moment with DateRangePicker
                // Works fine with DatePicker
                val selectedDate = datePickerState.selectedDateMillis?.let {
                    Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC)
                }
                selectedDate?.let {
                    val dateValue =
                        it.format(DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME))
                    fieldValue = TextFieldValue(dateValue, TextRange(dateValue.length))
                    onValueChange(dateValue)
                }
            },
        ) {
            DatePicker(
                state = datePickerState,
                dateValidator = { timestamp -> timestamp > Instant.now().toEpochMilli() },
                title = datePickerTitleResId?.let {
                    {
                        Text(
                            modifier = Modifier.padding(datePickerTitlePadding),
                            text = stringResource(it)
                        )
                    }
                }
            )
        }
    }
    Column {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp),//.weight(1f),
            enabled = false,
            readOnly = true,
            value = fieldValue,
            onValueChange = { fieldValue = it },
            label = labelResId?.let { { Text(stringResource(it)) } },
            leadingIcon = { Icon(imageVector = Icons.Outlined.DateRange, null) },
            trailingIcon = {
                Icon(imageVector = Icons.Outlined.ArrowDropDown, null, modifier = Modifier
                    .offset(x = 10.dp)
                    .clickable { onShowDialog() })
            },
            isError = inputWrapper.errorId != null,
            keyboardOptions = keyboardOptions,
            keyboardActions = remember {KeyboardActions(onAny = { onImeKeyAction() })},
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
fun PreviewSearchSingleSelectDialog() {
    val items = listOf(ListItemModel.defaultListItemModel(LocalContext.current))
    val isShowDialog = remember { mutableStateOf(true) }
    var isShowFullScreenDialog by remember { mutableStateOf(false) }

    if (isShowFullScreenDialog) {
        LocalContext.current.toast("another Full-screen Dialog")
    }
    /*
    SearchSingleSelectDialog(
        isShow = isShowDialog,
        title = stringResource(R.string.preview_blank_title),
        viewModel =
        onAddButtonClick = { isShowFullScreenDialog = true }
    ) { item -> println(item.headline) }

     */
}
