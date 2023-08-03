package com.oborodulin.home.common.ui.components.fab

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.theme.HomeComposableTheme

@Composable
fun FabComponent(
    enabled: Boolean = false,
    imageVector: ImageVector? = null,
    @DrawableRes painterResId: Int? = null,
    @StringRes textResId: Int? = null,
    @StringRes contentDescriptionResId: Int? = null,
    onClick: () -> Unit = {}
) {
    // https://www.appsloveworld.com/kotlin/100/7/jetpack-compose-how-to-disable-floatingaction-button
    val fabEnabled by remember { mutableStateOf(enabled) }
    val icon = @Composable {
        when (painterResId) {
            null -> imageVector?.let {
                Icon(
                    imageVector = it,
                    contentDescription = contentDescriptionResId?.let { stringResource(it) },
                    modifier = Modifier.padding(end = 4.dp),
                    //tint = if (enabledFab) LocalContentColor.current.copy(alpha = 0.4f) // LocalContentAlpha.current
                    //else DarkGray
                )
            }

            else -> Icon(
                painter = painterResource(painterResId),
                contentDescription = contentDescriptionResId?.let { stringResource(it) },
                modifier = Modifier.padding(end = 4.dp),
                //tint = if (enabledFab) LocalContentColor.current.copy(alpha = 0.4f) // LocalContentAlpha.current
                //else DarkGray
            )
        }
    }
    CompositionLocalProvider(
        LocalRippleTheme provides if (fabEnabled) LocalRippleTheme.current else NoRippleTheme
    ) {
        // https://stackoverflow.com/questions/72574071/unable-to-change-text-emphasis-using-localcontentalpha-in-material-design-3
        // https://stackoverflow.com/questions/70831743/customize-contentalpha-in-jetpack-compose-like-a-theme
        ExtendedFloatingActionButton(
            containerColor = if (fabEnabled) MaterialTheme.colorScheme.secondary else Gray,
            icon = {
                if (fabEnabled)
                // High emphasis
                    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
                        icon.invoke()
                    }
                else
                // Disabled emphasis
                    CompositionLocalProvider(
                        LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.38f
                        )
                    ) { icon.invoke() }
            },
            text = { textResId?.let { Text(stringResource(it)) } },
            onClick = { if (fabEnabled) onClick() },
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        )
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewFABComponent() {
    HomeComposableTheme {
        Surface {
            FabComponent(
                enabled = false,
                imageVector = Icons.Rounded.Add,
                textResId = R.string.preview_blank_fab_text,
                onClick = {})
        }
    }
}
