package com.oborodulin.home.common.ui.components.dialog.alert

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.oborodulin.home.common.R

@Composable
fun ErrorAlertDialogComponent(
    isShow: MutableState<Boolean>,
    text: String? = null,
    onTryAgain: () -> Unit = {},
    onDismiss: (() -> Unit)? = null
) {
    if (isShow.value) {
        AlertDialog(
            icon = { Icon(Icons.Outlined.Warning, null) },
            title = { Text(stringResource(R.string.dlg_error_title)) },
            text = text?.let { { Text(text) } },
            onDismissRequest = onDismiss ?: { isShow.value = false },
            confirmButton = {
                TextButton(onClick = {
                    isShow.value = false
                    onTryAgain()
                })
                { Text(text = stringResource(R.string.btn_try_again_lbl)) }
            },
            dismissButton = {
                TextButton(onClick = onDismiss ?: { isShow.value = false })
                { Text(text = stringResource(R.string.btn_dismiss_lbl)) }
            }
        )
    }
}