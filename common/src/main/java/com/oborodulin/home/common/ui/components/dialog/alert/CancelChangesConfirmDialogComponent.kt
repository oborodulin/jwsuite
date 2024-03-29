package com.oborodulin.home.common.ui.components.dialog.alert

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.oborodulin.home.common.R

@Composable
fun CancelChangesConfirmDialogComponent(
    isShow: MutableState<Boolean>,
    text: String? = null,
    @StringRes iconCntDescResId: Int = R.string.ic_cancel_changes_cnt_desc,
    onDismiss: (() -> Unit)? = null,
    onConfirm: () -> Unit
) {
    if (isShow.value) {
        AlertDialog(
            icon = { Icon(Icons.Outlined.Clear, stringResource(iconCntDescResId)) },
            title = { Text(stringResource(R.string.dlg_confirm_cancel_changes_title)) },
            text = text?.let { { Text(text) } },
            onDismissRequest = onDismiss ?: { isShow.value = false },
            confirmButton = {
                TextButton(onClick = {
                    isShow.value = false
                    onConfirm()
                })
                { Text(text = stringResource(R.string.btn_cancel_lbl)) }
            },
            dismissButton = {
                TextButton(onClick = onDismiss ?: { isShow.value = false })
                { Text(text = stringResource(R.string.btn_leave_lbl)) }
            }
        )
    }
}