package com.oborodulin.home.common.ui.components.buttons

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.theme.HomeComposableTheme

private const val TAG = "Common.ui.ButtonComponent"

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    imageVector: ImageVector? = null,
    @DrawableRes painterResId: Int? = null,
    @StringRes textResId: Int? = null,
    @StringRes contentDescriptionResId: Int? = null,
    onClick: () -> Unit,
    content: @Composable (RowScope.() -> Unit)? = null
) {
    val icon = @Composable {
        when (painterResId) {
            null -> imageVector?.let { iv ->
                Icon(
                    imageVector = iv,
                    contentDescription = contentDescriptionResId?.let { stringResource(it) },
                    modifier = Modifier.padding(end = 4.dp),
                    //tint = if (enabledFab) LocalContentColor.current.copy(alpha = 0.4f) // LocalContentAlpha.current
                    //else DarkGray
                )
            }

            else -> Icon(
                painter = painterResource(painterResId),
                contentDescription = contentDescriptionResId?.let { stringResource(it) },
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(24.dp),
                //tint = if (enabledFab) LocalContentColor.current.copy(alpha = 0.4f) // LocalContentAlpha.current
                //else DarkGray
            )
        }
    }
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick
    ) {
        if (content != null) {
            content()
        } else {
            icon()
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            textResId?.let { Text(text = stringResource(it)) }
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewButtonComponentEnabled() {
    HomeComposableTheme {
        Surface {
            ButtonComponent(
                enabled = true,
                imageVector = Icons.Rounded.Add,
                textResId = R.string.preview_blank_button_text,
                onClick = {})
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewButtonComponentDisabled() {
    HomeComposableTheme {
        Surface {
            ButtonComponent(
                enabled = false,
                imageVector = Icons.Rounded.Add,
                textResId = R.string.preview_blank_button_text,
                onClick = {})
        }
    }
}
