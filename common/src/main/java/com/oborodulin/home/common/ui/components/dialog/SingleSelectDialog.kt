package com.oborodulin.home.common.ui.components.dialog

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.search.SearchComponent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.home.common.util.toast
import java.util.Locale

@Composable
fun SingleSelectDialog(
    title: String,
    items: List<ListItemModel>,
    defaultSelected: Int,
    onClick: OnListItemEvent,
    onAddButtonClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    var selectedOption by remember { mutableStateOf(defaultSelected) }
    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Surface(
            modifier = Modifier
                .width(300.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = title)
                Spacer(modifier = Modifier.height(10.dp))
                if (items.isNotEmpty()) {
                    val searchState = remember { mutableStateOf(TextFieldValue("")) }
                    SearchComponent(searchState)
                    var filteredItems: List<ListItemModel>
                    LazyColumn(
                        state = rememberLazyListState(),
                        modifier = Modifier
                            .selectableGroup() // Optional, for accessibility purpose
                            .padding(8.dp)
                            .focusable(enabled = true)
                    ) {
                        val searchedText = searchState.value.text
                        filteredItems = if (searchedText.isEmpty()) {
                            items
                        } else {
                            val resultList = mutableListOf<ListItemModel>()
                            for (item in items) {
                                if (item.headline.lowercase(Locale.getDefault())
                                        .contains(searchedText.lowercase(Locale.getDefault()))
                                ) {
                                    resultList.add(item)
                                }
                            }
                            resultList
                        }
                        items(filteredItems.size) { index ->
                            SingleSelectRadioButton(
                                filteredItems[index],
                                filteredItems[selectedOption]
                            ) { selectedValue ->
                                selectedOption = filteredItems.indexOf(selectedValue)
                                onClick(filteredItems[selectedOption])
                                onDismissRequest.invoke()
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            onDismissRequest()
                            onAddButtonClick()
                        }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(36.dp),
                                imageVector = Icons.Outlined.Add,
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.width(width = 8.dp))
                            Text(text = stringResource(R.string.btn_add_lbl))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SingleSelectRadioButton(
    value: ListItemModel,
    selectedValue: ListItemModel,
    onClickListener: (ListItemModel) -> Unit
) {
    Row(Modifier
        .fillMaxWidth()
        .selectable(
            selected = (value == selectedValue),
            onClick = { onClickListener(value) }
        )
        .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (value == selectedValue),
            onClick = { onClickListener(value) }
        )
        Text(
            text = value.headline,
            style = MaterialTheme.typography.bodyMedium.merge(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewSingleSelectDialog() {
    val items = listOf(ListItemModel.defaultListItemModel(LocalContext.current))
    val currentSelectedItem by remember { mutableStateOf(items[0]) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        LocalContext.current.toast("another Full-screen Dialog")
    }
    SingleSelectDialog(
        title = stringResource(R.string.preview_blank_title),
        items = items,
        defaultSelected = items.indexOf(currentSelectedItem),
        onOptionClick = { item -> println(item.headline) },
        onAddButtonClick = { showDialog = true }
    ) {
        showDialog = false
    }
}
