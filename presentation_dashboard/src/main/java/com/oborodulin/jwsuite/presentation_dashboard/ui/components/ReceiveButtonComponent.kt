package com.oborodulin.jwsuite.presentation_dashboard.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.components.buttons.ButtonComponent
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.jwsuite.presentation.R

private const val TAG = "Presentation_Dashboard.ReceiveButtonComponent"

@Composable
fun ReceiveButtonComponent(
    modifier: Modifier = Modifier, enabled: Boolean = false, onClick: () -> Unit = {}
) {
    ButtonComponent(
        modifier = Modifier
            .padding(end = 8.dp)
            .then(modifier),
        enabled = enabled,
        painterResId = R.drawable.ic_receive_24,
        textResId = R.string.btn_receive_lbl,
        contentDescriptionResId = R.string.btn_receive_cnt_desc,
        onClick = onClick
    )
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewReceiveButtonComponentEnabled() {
    HomeComposableTheme {
        Surface {
            ReceiveButtonComponent(enabled = true, onClick = {})
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewReceiveButtonComponentDisabled() {
    HomeComposableTheme {
        Surface {
            ReceiveButtonComponent(enabled = false, onClick = {})
        }
    }
}
