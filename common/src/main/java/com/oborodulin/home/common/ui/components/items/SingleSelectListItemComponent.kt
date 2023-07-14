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
import timber.log.Timber

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Common.ui.SingleSelectListItemComponent"

// https://m3.material.io/components/lists/specs
@Composable
fun SingleSelectListItemComponent(item: ListItemModel, onClick: OnListItemEvent) {
    Row(
        modifier = Modifier
            .clickable(onClick = {
                Timber
                    .tag(TAG)
                    .d("onClick() item = %s", item)
                onClick(item)
            }
            )
            //.background(colorResource(id = R.color.colorPrimaryDark))
            .height(56.dp)
            .fillMaxWidth()
            .padding(PaddingValues(8.dp, 16.dp))
    ) {
        Text(text = item.headline, fontSize = 18.sp)
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewSingleSelectListItemComponent() {
    SingleSelectListItemComponent(
        item = ListItemModel.defaultListItemModel(LocalContext.current)
    ) { }
}
