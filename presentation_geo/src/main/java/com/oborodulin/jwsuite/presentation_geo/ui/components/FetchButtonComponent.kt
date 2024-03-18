package com.oborodulin.jwsuite.presentation_geo.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.data.network.ConnectionState
import com.oborodulin.home.common.ui.components.buttons.ButtonComponent
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.LogLevel
import com.oborodulin.home.common.util.connectivityState
import com.oborodulin.jwsuite.presentation_geo.R
import timber.log.Timber

private const val TAG = "Common.ui.FetchButtonComponent"

@Composable
fun FetchButtonComponent(
    modifier: Modifier = Modifier, enabled: Boolean = false, onClick: () -> Unit = {}
) {
    // This will cause re-composition on every network state change
    val connection by connectivityState()

    val isConnected = connection === ConnectionState.Available

    if (isConnected) {
        if (LogLevel.LOG_NETWORK) {
            Timber.tag(TAG).d("connectivity is available")
        }
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
    } else {
        if (LogLevel.LOG_NETWORK) {
            Timber.tag(TAG).d("No Internet Connectivity")
        }
        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = null,
        )
    }
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