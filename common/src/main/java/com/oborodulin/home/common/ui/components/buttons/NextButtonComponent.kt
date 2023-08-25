package com.oborodulin.home.common.ui.components.buttons

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.theme.HomeComposableTheme

private const val TAG = "Common.ui.NextButtonComponent"

@Composable
fun NextButtonComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    @StringRes contentDescriptionResId: Int? = null,
    onClick: () -> Unit = {}
) {
    ButtonComponent(
        modifier = modifier,
        enabled = enabled,
        imageVector = Icons.Outlined.ArrowForward,
        textResId = R.string.btn_next_lbl,
        contentDescriptionResId = contentDescriptionResId,
        onClick = onClick
    )
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewNextButtonComponentEnabled() {
    HomeComposableTheme {
        Surface {
            NextButtonComponent(
                enabled = true,
                onClick = {}
            )
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewNextButtonComponentDisabled() {
    HomeComposableTheme {
        Surface {
            NextButtonComponent(
                enabled = false,
                onClick = {}
            )
        }
    }
}
