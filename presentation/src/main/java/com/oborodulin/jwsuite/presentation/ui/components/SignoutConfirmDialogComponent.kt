package com.oborodulin.jwsuite.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.oborodulin.jwsuite.presentation.R

@Composable
fun SignoutConfirmDialogComponent(
    isShow: MutableState<Boolean>,
    text: String? = null,
    onDismiss: (() -> Unit)? = null,
    onConfirm: () -> Unit
) {
    if (isShow.value) {
        AlertDialog(
            icon = {
                Icon(
                    Icons.Outlined.ExitToApp, stringResource(R.string.btn_signout_cnt_desc)
                )
            },
            title = { Text(stringResource(R.string.dlg_confirm_signout_title)) },
            text = text?.let { { Text(text) } },
            onDismissRequest = onDismiss ?: { isShow.value = false },
            confirmButton = {
                TextButton(onClick = {
                    isShow.value = false
                    onConfirm()
                })
                { Text(text = stringResource(R.string.btn_signout_lbl)) }
            },
            dismissButton = {
                TextButton(onClick = onDismiss ?: { isShow.value = false })
                { Text(text = stringResource(com.oborodulin.home.common.R.string.btn_cancel_lbl)) }
            }
        )
    }
}