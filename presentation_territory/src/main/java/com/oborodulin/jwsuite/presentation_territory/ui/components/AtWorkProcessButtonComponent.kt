package com.oborodulin.jwsuite.presentation_territory.ui.components

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oborodulin.home.common.ui.components.buttons.ButtonComponent
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.jwsuite.presentation_territory.R

private const val TAG = "Presentation_Territory.AtWorkProcessButtonComponent"

@Composable
fun AtWorkProcessButtonComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onClick: () -> Unit = {}
) {
    ButtonComponent(
        modifier = modifier,
        enabled = enabled,
        imageVector = Icons.Outlined.ThumbUp,
        textResId = R.string.btn_at_work_process_lbl,
        contentDescriptionResId = R.string.at_work_process_cnt_desc,
        onClick = onClick
    )
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewAtWorkProcessButtonComponentEnabled() {
    HomeComposableTheme {
        Surface {
            AtWorkProcessButtonComponent(enabled = true, onClick = {})
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewAtWorkProcessButtonComponentDisabled() {
    HomeComposableTheme {
        Surface {
            AtWorkProcessButtonComponent(enabled = false, onClick = {})
        }
    }
}