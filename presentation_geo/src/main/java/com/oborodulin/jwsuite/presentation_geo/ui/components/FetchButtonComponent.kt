package com.oborodulin.jwsuite.presentation_geo.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.components.buttons.ButtonComponent
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.jwsuite.presentation_geo.R

private const val TAG = "Common.ui.FetchButtonComponent"

@Composable
fun FetchButtonComponent(
    modifier: Modifier = Modifier, enabled: Boolean = false, onClick: () -> Unit = {}
) {
    ButtonComponent(
        modifier = Modifier
            .padding(8.dp)
            .then(modifier),
        enabled = enabled,
        painterResId = R.drawable.ic_download_24,
        textResId = R.string.btn_fetch_lbl,
        contentDescriptionResId = R.string.fetch_cnt_desc,
        onClick = onClick
    )
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewFetchButtonComponentEnabled() {
    HomeComposableTheme {
        Surface {
            FetchButtonComponent(
                enabled = true,
                onClick = {}
            )
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewFetchButtonComponentDisabled() {
    HomeComposableTheme {
        Surface {
            FetchButtonComponent(
                enabled = false,
                onClick = {}
            )
        }
    }
}