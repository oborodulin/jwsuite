package com.oborodulin.home.common.ui.components.items

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.model.ListItemModel

@Composable
fun OptionSelectRadioButtonComponent(
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
fun PreviewOptionSelectRadioButtonComponent() {
    OptionSelectRadioButtonComponent(
        value = ListItemModel.defaultListItemModel(LocalContext.current),
        selectedValue = ListItemModel.defaultListItemModel(LocalContext.current),
    ) {
    }
}
