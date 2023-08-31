package com.oborodulin.home.common.ui.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import timber.log.Timber

private const val TAG = "Common.ui.IconComponent"

@Composable
fun IconComponent(
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    @DrawableRes painterResId: Int? = null,
    icon: @Composable (() -> Unit)? = null,
    @StringRes contentDescriptionResId: Int? = null,
    size: Dp = 24.dp
): Unit? {
    Timber.tag(TAG).d("IconComponent(...) called")
    return icon?.let { it() } ?: when (painterResId) {
        null -> imageVector?.let { iv ->
            Icon(
                imageVector = iv,
                contentDescription = contentDescriptionResId?.let { stringResource(it) },
                modifier = Modifier
                    //.padding(end = 4.dp)
// https://stackoverflow.com/questions/64377952/material-icon-size-adjustment-in-jetpack-compose
                    .size(size)
                    .then(modifier),
                //tint = if (enabledFab) LocalContentColor.current.copy(alpha = 0.4f) // LocalContentAlpha.current
                //else DarkGray
            )
        }

        else -> Icon(
            painter = painterResource(painterResId),
            contentDescription = contentDescriptionResId?.let { stringResource(it) },
            modifier = Modifier
                //.padding(end = 4.dp)
                .size(size)
                .then(modifier),
            //tint = if (enabledFab) LocalContentColor.current.copy(alpha = 0.4f) // LocalContentAlpha.current
            //else DarkGray
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewIconComponent() {
    HomeComposableTheme {
        Surface {
            IconComponent(icon = {
                Icon(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(24.dp),
                    painter = painterResource(R.drawable.outline_photo_24),
                    contentDescription = stringResource(R.string.preview_blank_descr),
                )
            }
            )
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewImageVectorIconComponent() {
    HomeComposableTheme { Surface { IconComponent(imageVector = Icons.Default.AccountCircle) } }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewPainterResIconComponent() {
    HomeComposableTheme { Surface { IconComponent(painterResId = R.drawable.outline_photo_24) } }
}