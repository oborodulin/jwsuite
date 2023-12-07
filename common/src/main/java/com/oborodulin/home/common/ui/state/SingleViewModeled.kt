package com.oborodulin.home.common.ui.state

import com.oborodulin.home.common.ui.components.field.util.Focusable
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

interface SingleViewModeled<T : Any, A : UiAction, E : UiSingleEvent, F : Focusable> :
    MviViewModeled<T, A, E> {
    val id: StateFlow<InputWrapper>
    val isUiStateChanged: StateFlow<Boolean>
    val events: Flow<ScreenEvent>

    fun id(): UUID?
    fun onContinueClick(isPartialInputsValid: Boolean = false, onSuccess: () -> Unit)
    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: F, isFocused: Boolean)
    fun moveFocusImeAction()
}