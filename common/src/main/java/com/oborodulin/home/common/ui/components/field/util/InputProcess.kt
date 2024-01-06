package com.oborodulin.home.common.ui.components.field.util

import android.content.Context
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.toast
import timber.log.Timber

private const val TAG = "Common.ui.inputProcess"

@OptIn(ExperimentalComposeUiApi::class)
fun <F : Focusable> inputProcess(
    context: Context,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?,
    event: ScreenEvent,
    focusRequesters: Map<F, InputFocusRequester>
) {
    if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# inputProcess(...) called")
    when (event) {
        is ScreenEvent.ShowToast -> context.toast(event.messageId)
        is ScreenEvent.UpdateKeyboard -> {
            if (event.show) keyboardController?.show() else keyboardController?.hide()
        }

        is ScreenEvent.ClearFocus -> focusManager.clearFocus()
        is ScreenEvent.RequestFocus -> focusRequesters[event.textField]?.focusRequester?.requestFocus()
        is ScreenEvent.MoveFocus -> focusManager.moveFocus(event.direction)
    }
}
