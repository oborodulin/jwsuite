package com.oborodulin.home.common.ui.components.dialog.alert

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.oborodulin.home.common.R
import com.oborodulin.home.common.ui.components.IconComponent

@Composable
fun AlertDialogComponent(
    isShow: MutableState<Boolean>,
    imageVector: ImageVector? = null,
    @DrawableRes painterResId: Int? = null,
    @StringRes contentDescriptionResId: Int? = null,
    @StringRes titleResId: Int,
    text: String? = null,
    onDismiss: (() -> Unit)? = null,
    onConfirm: () -> Unit
) {
    if (isShow.value) {
        AlertDialog(
            icon = {
                IconComponent(
                    imageVector = imageVector,
                    painterResId = painterResId,
                    contentDescriptionResId = contentDescriptionResId
                )
            },
            title = { Text(stringResource(titleResId)) },
            text = text?.let { { Text(text) } },
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