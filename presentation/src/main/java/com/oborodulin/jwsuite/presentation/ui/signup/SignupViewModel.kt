package com.oborodulin.jwsuite.presentation.ui.signup

import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SignupViewModel : DialogViewModeled<Any, SignupUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val username: StateFlow<InputWrapper>
    val password: StateFlow<InputWrapper>
    val confirmPassword: StateFlow<InputWrapper>

    val areInputsValid: StateFlow<Boolean>

    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: SignupFields, isFocused: Boolean)
    fun moveFocusImeAction()
}