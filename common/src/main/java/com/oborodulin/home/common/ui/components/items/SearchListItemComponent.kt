package com.oborodulin.home.common.ui.components.items

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.util.OnListItemEvent

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Common.UI"

@Composable
fun SearchListItemComponent(
    item: ListItemModel,
    onClick: OnListItemEvent
) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onClick(item) })
            //.background(colorResource(id = R.color.colorPrimaryDark))
            .height(57.dp)
            .fillMaxWidth()
            .padding(PaddingValues(8.dp, 16.dp))
    ) {
        Text(text = item.headline, fontSize = 18.sp)
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewSearchListItemComponent() {
    SearchListItemComponent(
        item = ListItemModel.defaultListItemModel(LocalContext.current),
        onClick = { })
}
