package com.oborodulin.home.common.ui.components.field

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.OnCheckedChange
import timber.log.Timber

private const val TAG = "Common.ui.SwitchComponent"

@Composable
fun SwitchComponent(
    componentModifier: Modifier = Modifier,
    switchModifier: Modifier = Modifier,
    inputWrapper: InputWrapper, // string
    @StringRes labelResId: Int? = null,
    onCheckedChange: OnCheckedChange,
    colors: SwitchColors = SwitchDefaults.colors()
) {
    Timber.tag(TAG).d("SwitchComponent(...) called")
    var isChecked by remember { mutableStateOf(inputWrapper.value.toBoolean()) } // boolean
    Timber.tag(TAG).d(
        "SwitchComponent(...): isChecked = %s; inputWrapper.value = %s",
        isChecked,
        inputWrapper.value
    )
    /*    if (isChecked != inputWrapper.value.toBoolean()) isChecked = inputWrapper.value.toBoolean()
        Timber.tag(TAG).d(
            "SwitchComponent(...): isChecked = %s; inputWrapper = %s",
            isChecked,
            inputWrapper
        )*/
    Column {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .clickable(
                    indication = rememberRipple(color = MaterialTheme.colorScheme.primary),
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { onCheckedChange(!isChecked) }
                )
                .requiredHeight(ButtonDefaults.MinHeight)
                .padding(4.dp)
                .then(componentModifier),
            horizontalArrangement = Arrangement.SpaceBetween) {
            labelResId?.let {
                Text(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .weight(4f),
                    text = stringResource(it)
                )
            }
            Switch(
                modifier = switchModifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .weight(1f),
                checked = isChecked, // boolean
                onCheckedChange = {
                    isChecked = it
                    onCheckedChange(it)
                },
                thumbContent = {
                    Icon(
                        imageVector = if (isChecked) Icons.Filled.Check else Icons.Filled.Close,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize)
                    )
                },
                colors = colors
            )
        }
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
fun PreviewSwitchComponent() {
    HomeComposableTheme {
        Surface {
            SwitchComponent(switchModifier = Modifier,
                inputWrapper = InputWrapper(
                    value = "true",
                    errorId = R.string.preview_blank_text_field_err
                ),
                labelResId = R.string.preview_blank_text_field_lbl,
                onCheckedChange = {})
        }
    }
}
