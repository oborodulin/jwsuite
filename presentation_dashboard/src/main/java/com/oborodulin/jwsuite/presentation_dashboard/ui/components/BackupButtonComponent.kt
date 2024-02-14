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
import com.oborodulin.jwsuite.presentation_dashboard.R

private const val TAG = "Presentation_Dashboard.BackupButtonComponent"

@Composable
fun BackupButtonComponent(
    modifier: Modifier = Modifier, enabled: Boolean = false, onClick: () -> Unit = {}
) {
    ButtonComponent(
        modifier = Modifier
            .padding(end = 8.dp)
            .then(modifier),
        enabled = enabled,
        painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_backup_24,
        textResId = R.string.btn_backup_lbl,
        contentDescriptionResId = R.string.btn_backup_cnt_desc,
        onClick = onClick
    )
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewBackupButtonComponentEnabled() {
    HomeComposableTheme {
        Surface {
            BackupButtonComponent(enabled = true, onClick = {})
        }
    }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewBackupButtonComponentDisabled() {
    HomeComposableTheme {
        Surface {
            BackupButtonComponent(enabled = false, onClick = {})
        }
    }
}
