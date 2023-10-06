package com.oborodulin.jwsuite.presentation.ui.login

import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface LoginViewModel : DialogViewModeled<Any, LoginUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val password: StateFlow<InputWrapper>

    val areInputsValid: StateFlow<Boolean>

    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: LoginFields, isFocused: Boolean)
    fun moveFocusImeAction()
}