package com.oborodulin.home.common.ui.components.field

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.util.Constants
import com.oborodulin.home.common.util.OnValueChange
import com.oborodulin.home.common.util.toast
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private const val TAG = "Common.ui.DatePickerComponent"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    isShow: Boolean,
    title: String,
    onDismissRequest: () -> Unit,
    onValueChange: OnValueChange
) {
    if (isShow) {
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
                // Seems broken at the moment with DateRangePicker
                // Works fine with DatePicker
                val selectedDate = datePickerState.selectedDateMillis?.let {
                    Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC)
                }
                selectedDate?.let {
                    onValueChange(it.format(DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME)))
                }
            },
        ) {
            DatePicker(
                state = datePickerState,
                dateValidator = { timestamp ->
                    timestamp > Instant.now().toEpochMilli()
                },
                title = {
                    Text(
                        modifier = Modifier.padding(datePickerTitlePadding),
                        text = title
                    )
                }
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
