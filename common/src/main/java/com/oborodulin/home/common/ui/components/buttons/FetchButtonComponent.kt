package com.oborodulin.home.common.ui.components.buttons

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.R
import com.oborodulin.home.common.data.network.ConnectionState
import com.oborodulin.home.common.ui.components.text.ErrorTextComponent
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.LogLevel
import com.oborodulin.home.common.util.connectivityState
import timber.log.Timber

private const val TAG = "Common.ui.FetchButtonComponent"

@Composable
fun FetchButtonComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    content: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    // This will cause re-composition on every network state change
    val connection by connectivityState()
    val isConnectionAvailable = connection === ConnectionState.Available
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            content?.invoke()
            if (LogLevel.LOG_NETWORK && isConnectionAvailable) {
                Timber.tag(TAG).d("Internet connectivity is available")
            }
            ButtonComponent(
                modifier = Modifier
                    .padding(8.dp)
                    .then(modifier),
                enabled = enabled && isConnectionAvailable,
                painterResId = R.drawable.ic_download_24,
                textResId = R.string.btn_fetch_lbl,
                contentDescriptionResId = R.string.btn_fetch_cnt_desc,
                onClick = onClick
            )
        }
        if (isConnectionAvailable.not()) {
            if (LogLevel.LOG_NETWORK) {
                Timber.tag(TAG).d("No Internet Connectivity")
            }
            ErrorTextComponent(
                painterResId = R.drawable.ic_no_internet_24,
                contentDescResId = R.string.ic_no_internet_cnt_desc,
                textResId = R.string.no_internet_error
            )
        }
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