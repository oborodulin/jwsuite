package com.oborodulin.jwsuite.presentation_territory.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oborodulin.home.common.ui.components.buttons.ButtonComponent
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.jwsuite.presentation_territory.R

private const val TAG = "Presentation.HandOutButtonComponent"

@Composable
fun HandOutButtonComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    @StringRes contentDescriptionResId: Int? = null,
    onClick: () -> Unit = {}
) {
    ButtonComponent(
        modifier = modifier,
        enabled = enabled,
        painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_hand_map_24,
        textResId = R.string.fab_territory_hand_out_text,
        contentDescriptionResId = contentDescriptionResId,
        onClick = onClick
    )
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewHandOutButtonComponentEnabled() {
    HomeComposableTheme {
        Surface {
            HandOutButtonComponent(enabled = true, onClick = {})
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewHandOutButtonComponentDisabled() {
    HomeComposableTheme {
        Surface {
            HandOutButtonComponent(enabled = false, onClick = {})
        }
    }
}
