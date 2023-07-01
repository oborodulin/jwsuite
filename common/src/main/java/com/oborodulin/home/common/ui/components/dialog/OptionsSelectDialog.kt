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
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.items.OptionSelectRadioButton
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.util.toast

@Composable
fun OptionSelectDialog(
    title: String,
    optionsList: List<ListItemModel>,
    defaultSelected: Int,
    onOptionClick: (ListItemModel) -> Unit,
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
                if (optionsList.isNotEmpty()) {
                    LazyColumn(
                        state = rememberLazyListState(),
                        modifier = Modifier
                            .selectableGroup() // Optional, for accessibility purpose
                            .padding(8.dp)
                            .focusable(enabled = true)
                    ) {
                        items(optionsList.size) { index ->
                            OptionSelectRadioButton(
                                optionsList[index],
                                optionsList[selectedOption]
                            ) { selectedValue ->
                                selectedOption = optionsList.indexOf(selectedValue)
                                onOptionClick.invoke(optionsList[selectedOption])
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

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewOptionSelectDialog() {
    val items = listOf(ListItemModel.defaultListItemModel(LocalContext.current))
    val currentSelectedItem by remember { mutableStateOf(items[0]) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        LocalContext.current.toast("another Full-screen Dialog")
    }
    OptionSelectDialog(
        title = stringResource(R.string.preview_blank_title),
        optionsList = items,
        defaultSelected = items.indexOf(currentSelectedItem),
        onOptionClick = { item -> println(item.headline) },
        onAddButtonClick = { showDialog = true }
    ) {
        showDialog = false
    }
}
