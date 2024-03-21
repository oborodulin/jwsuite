package com.oborodulin.home.common.ui.components.tooltip

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.theme.HomeComposableTheme

private const val TAG = "Common.ui.TooltipBoxComponent"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TooltipBoxComponent(
    modifier: Modifier = Modifier,
    title: String? = null,
    @StringRes contentDescriptionResId: Int? = null,
    action: @Composable (() -> Unit)? = null,
    tint: Color = Color(0xFF6650a4), // Purple40
    text: @Composable () -> Unit
) {
    val tooltipState = rememberTooltipState(isPersistent = true)
    // https://www.develou.com/tooltips-android/
    TooltipBox(
        modifier = modifier,
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
        tooltip = {
            RichTooltip(
                title = title?.let { { Text(text = it) } },
                action = action,
                text = text
            )
        },
        state = tooltipState
    )
    {
        IconButton(onClick = {}, modifier = Modifier
            .padding(top = 2.dp, bottom = 4.dp)
            .size(18.dp)) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = contentDescriptionResId?.let { stringResource(it) },
                modifier = Modifier.size(18.dp),
                tint = tint
            )
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTooltipBoxComponent() {
    HomeComposableTheme {
        Surface {
            TooltipBoxComponent(
                title = stringResource(R.string.preview_blank_tooltip_title),
                text = { Text(stringResource(R.string.preview_blank_tooltip_text)) }
            )
        }
    }
}