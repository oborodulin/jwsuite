package com.oborodulin.home.common.ui.components.radio

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.IconComponent
import com.oborodulin.home.common.ui.theme.HomeComposableTheme

private const val TAG = "Common.ui.RadioGroupComponent"

@Composable
fun <T : Any, M : Map<String, T>> RadioGroupComponent(
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    @DrawableRes painterResId: Int? = null,
    @StringRes labelResId: Int? = null,
    @StringRes contentDescriptionResId: Int? = null,
    radioOptions: M,
    selectedItem: T,
    onClick: (T) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .requiredHeight(74.dp)// height(IntrinsicSize.Min)
            .selectableGroup()
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconComponent(
                imageVector = imageVector,
                painterResId = painterResId,
                contentDescriptionResId = contentDescriptionResId
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            labelResId?.let { Text(text = stringResource(it)) }
        }
        LazyRow(
            modifier = Modifier
                .width(370.dp)
                .height(46.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(radioOptions.entries.size) {
                RadioButtonComponent(
                    modifier = Modifier.weight(1f / radioOptions.entries.size),
                    label = radioOptions.keys.elementAt(it),
                    selected = (radioOptions.values.elementAt(it) == selectedItem),
                    onClick = { onClick(radioOptions.values.elementAt(it)) }
                )
            }
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRadioGroupComponent() {
    val radioOptions = mapOf(
        "да" to RadioBooleanType.YES,
        "нет" to RadioBooleanType.NO,
        "не указано" to RadioBooleanType.UNDEF
    )
    var selectedItem by remember { mutableStateOf(radioOptions.values.last()) }
    HomeComposableTheme {
        Surface {
            RadioGroupComponent(
                imageVector = Icons.Rounded.List,
                labelResId = R.string.preview_blank_radio_group_text,
                radioOptions = radioOptions,
                selectedItem = selectedItem,
                onClick = { selectedItem = it }
            )
        }
    }
}