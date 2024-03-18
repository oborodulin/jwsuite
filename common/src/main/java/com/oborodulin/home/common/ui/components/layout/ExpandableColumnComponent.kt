package com.oborodulin.home.common.ui.components.layout

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.LogLevel.LOG_UI_COMPONENTS
import timber.log.Timber

private const val TAG = "Common.ui.ExpandableColumnComponent"

// https://codingwithrashid.com/create-expandable-card-in-android-jetpack-compose/
// https://stackoverflow.com/questions/69951212/how-can-i-keep-expand-item-of-a-column-control-in-compose
@Composable
fun ExpandableColumnComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isExpanded: Boolean = false,
    @StringRes titleResId: Int,
    content: @Composable ColumnScope.() -> Unit
) {
    if (LOG_UI_COMPONENTS) {
        Timber.tag(TAG).d("ExpandableColumnComponent(...) called")
    }
    var expanded by rememberSaveable { mutableStateOf(isExpanded) }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                1.dp, MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(16.dp)
            )
            .animateContentSize() // This is where the magic happens!
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(titleResId),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )
            IconButton(onClick = { if (enabled) expanded = !expanded }) {
                if (expanded) {
                    Icon(Icons.Outlined.KeyboardArrowUp, null)
                } else {
                    Icon(Icons.Outlined.KeyboardArrowDown, null)
                }
            }
        }
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .then(modifier)
            ) {
                content()
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewExpandableColumnComponent() {
    HomeComposableTheme {
        Surface {
            ExpandableColumnComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                titleResId = R.string.preview_blank_column_title
            ) {}
        }
    }
}
