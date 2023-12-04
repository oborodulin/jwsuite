package com.oborodulin.home.common.ui.components.fab

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.oborodulin.home.common.ui.components.IconComponent
import com.oborodulin.home.common.ui.theme.HomeComposableTheme

// https://itnext.io/floating-action-button-in-jetpack-compose-with-material-3-10ba8bff415a#c409
@Composable
fun FabComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    imageVector: ImageVector? = null,
    @DrawableRes painterResId: Int? = null,
    @StringRes contentDescriptionResId: Int? = null,
    onClick: () -> Unit = {}
) {
    // https://www.appsloveworld.com/kotlin/100/7/jetpack-compose-how-to-disable-floatingaction-button
    val icon = @Composable {
        IconComponent(
            imageVector = imageVector,
            painterResId = painterResId,
            contentDescriptionResId = contentDescriptionResId
        )
    }
    CompositionLocalProvider(
        LocalRippleTheme provides if (enabled) LocalRippleTheme.current else NoRippleTheme
    ) {
        // https://stackoverflow.com/questions/72574071/unable-to-change-text-emphasis-using-localcontentalpha-in-material-design-3
        // https://stackoverflow.com/questions/70831743/customize-contentalpha-in-jetpack-compose-like-a-theme
        FloatingActionButton(
            modifier = modifier,
            containerColor = if (enabled) MaterialTheme.colorScheme.primary else Gray,
            onClick = { if (enabled) onClick() }
        ) {
            if (enabled)
            // High emphasis
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimary) {
                    icon.invoke()
                }
            else
            // Disabled emphasis
                CompositionLocalProvider(
                    LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.38f
                    )
                ) { icon.invoke() }
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewFabComponentEnabled() {
    HomeComposableTheme {
        Surface {
            FabComponent(
                enabled = true,
                imageVector = Icons.Rounded.Add,
                onClick = {})
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewFabComponentDisabled() {
    HomeComposableTheme {
        Surface {
            FabComponent(
                enabled = false,
                imageVector = Icons.Rounded.Add,
                onClick = {})
        }
    }
}
