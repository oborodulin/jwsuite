package com.oborodulin.home.common.ui.components.field

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.IconComponent
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.LogLevel
import com.oborodulin.home.common.util.LogLevel.LOG_UI_COMPONENTS
import com.oborodulin.home.common.util.OnCheckedChange
import timber.log.Timber

private const val TAG = "Common.ui.SwitchComponent"

@Composable
fun SwitchComponent(
    componentModifier: Modifier = Modifier,
    switchModifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    imageVector: ImageVector? = null,
    @DrawableRes painterResId: Int? = null,
    @StringRes labelResId: Int? = null,
    lableStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    @StringRes contentDescriptionResId: Int? = null,
    inputWrapper: InputWrapper, // string
    onCheckedChange: OnCheckedChange,
    colors: SwitchColors = SwitchDefaults.colors()
) {
    if (LOG_UI_COMPONENTS) Timber.tag(TAG).d("SwitchComponent(...) called")
    var isChecked by remember { mutableStateOf(inputWrapper.value.toBoolean()) } // boolean
    if (LOG_UI_COMPONENTS) Timber.tag(TAG).d(
        "SwitchComponent: isChecked = %s; inputWrapper.value = %s",
        isChecked,
        inputWrapper.value
    )
    /*    if (isChecked != inputWrapper.value.toBoolean()) isChecked = inputWrapper.value.toBoolean()
        if (LOG_UI_COMPONENTS) Timber.tag(TAG).d(
            "SwitchComponent: isChecked = %s; inputWrapper = %s",
            isChecked,
            inputWrapper
        )*/
    Column {
        Row(modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(
                indication = rememberRipple(color = MaterialTheme.colorScheme.primary),
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onCheckedChange(!isChecked) }
            )
            // https://developer.android.com/jetpack/compose/accessibility#merge-elements
            .semantics(mergeDescendants = true) {}
            .requiredHeight(ButtonDefaults.MinHeight)
            .padding(4.dp)
            .then(componentModifier),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = horizontalArrangement) {
            val labelRowModifier = when (horizontalArrangement) {
                Arrangement.Start -> Modifier
                else -> Modifier.weight(1f)
            }
            Row(
                modifier = labelRowModifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconComponent(
                    imageVector = imageVector,
                    painterResId = painterResId,
                    contentDescriptionResId = contentDescriptionResId,
                    size = 36.dp
                )
                labelResId?.let {
                    Text(
                        modifier = Modifier
                            .padding(end = 4.dp),
                        text = stringResource(it),
                        style = lableStyle
                    )
                }
            }
            Switch(
                modifier = switchModifier.padding(vertical = 4.dp, horizontal = 8.dp),
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
                painterResId = R.drawable.outline_photo_black_24,
                labelResId = R.string.preview_blank_text_field_lbl,
                onCheckedChange = {})
        }
    }
}
