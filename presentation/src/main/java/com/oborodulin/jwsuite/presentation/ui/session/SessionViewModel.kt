package com.oborodulin.jwsuite.presentation.ui.session

import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SessionViewModel : DialogViewModeled<SessionUi, SessionUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val username: StateFlow<InputWrapper>
    val pin: StateFlow<InputWrapper>
    val confirmPin: StateFlow<InputWrapper>

    val areSignupInputsValid: StateFlow<Boolean>

    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: SessionFields, isFocused: Boolean)
    fun moveFocusImeAction()
}