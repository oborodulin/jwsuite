package com.oborodulin.home.common.ui.components.buttons

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.theme.HomeComposableTheme

private const val TAG = "Common.ui.AddIconButtonComponent"

@Composable
fun AddIconButtonComponent(@StringRes contentDescriptionResId: Int? = null, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            modifier = Modifier.size(36.dp),
            //.weight(2.5f),
            imageVector = Icons.Outlined.Add,
            contentDescription = contentDescriptionResId?.let { stringResource(it) }
        )
        /*Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ...
            Spacer(
                modifier = Modifier
                    .width(width = 8.dp)
                    .weight(1f)
            )
            Text(
                modifier = Modifier.weight(6.5f),
                text = stringResource(R.string.btn_add_lbl)
            )
        }*/
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewAddIconButtonComponent() {
    HomeComposableTheme { Surface { AddIconButtonComponent(onClick = {}) } }
}
