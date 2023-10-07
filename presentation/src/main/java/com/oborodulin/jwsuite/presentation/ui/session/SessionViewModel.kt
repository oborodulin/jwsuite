package com.oborodulin.jwsuite.presentation.ui.session

import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.domain.model.congregation.Role
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SessionViewModel : DialogViewModeled<Any, SessionUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val isSigned: StateFlow<Boolean>
    val isLogged: StateFlow<Boolean>
    val roles: Flow<List<Role>>

    val username: StateFlow<InputWrapper>
    val password: StateFlow<InputWrapper>
    val confirmPassword: StateFlow<InputWrapper>

    val areInputsValid: StateFlow<Boolean>

    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: SessionFields, isFocused: Boolean)
    fun moveFocusImeAction()
}