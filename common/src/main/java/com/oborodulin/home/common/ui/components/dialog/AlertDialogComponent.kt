package com.oborodulin.home.common.ui.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.oborodulin.home.common.R

@Composable
fun AlertDialogComponent(
    isShow: MutableState<Boolean>,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    onConfirm: () -> Unit
) {
    if (isShow.value) {
        AlertDialog(
            title = title,
            text = text,
            onDismissRequest = onDismiss ?: { isShow.value = false },
            confirmButton = {
                TextButton(onClick = {
                    isShow.value = false
                    onConfirm()
                })
                { Text(text = stringResource(R.string.btn_ok_lbl)) }
            },
            dismissButton = {
                TextButton(onClick = onDismiss ?: { isShow.value = false })
                { Text(text = stringResource(R.string.btn_cancel_lbl)) }
            }
        )
    }
}