package com.oborodulin.home.common.ui.components.fab

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import timber.log.Timber

private const val TAG = "Common.ui.AddFabComponent"

@Composable
fun AddFabComponent(
    modifier: Modifier = Modifier,
    @StringRes textResId: Int? = null,
    @StringRes contentDescriptionResId: Int? = null,
    onClick: () -> Unit = {}
) {
    Timber.tag(TAG).d("AddFabComponent(...) called")
    ExtFabComponent(
        modifier = modifier,
        enabled = true,
        imageVector = Icons.Outlined.Add,
        textResId = textResId,
        contentDescriptionResId = contentDescriptionResId,
        onClick = onClick
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewAddFabComponent() {
    HomeComposableTheme { Surface { AddFabComponent() } }
}
