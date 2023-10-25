package com.oborodulin.jwsuite.presentation.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oborodulin.home.common.ui.components.buttons.ButtonComponent
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.jwsuite.presentation.R

private const val TAG = "Presentation.LoginButtonComponent"

@Composable
fun LoginButtonComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    @StringRes contentDescriptionResId: Int? = null,
    onClick: () -> Unit = {}
) {
    ButtonComponent(
        modifier = modifier,
        enabled = enabled,
        imageVector = Icons.Outlined.Done,
        textResId = R.string.btn_login_lbl,
        contentDescriptionResId = contentDescriptionResId,
        onClick = onClick
    )
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLoginButtonComponentEnabled() {
    HomeComposableTheme { Surface { LoginButtonComponent(enabled = true, onClick = {}) } }
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLoginButtonComponentDisabled() {
    HomeComposableTheme { Surface { LoginButtonComponent(enabled = false, onClick = {}) } }
}
