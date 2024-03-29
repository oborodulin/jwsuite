package com.oborodulin.jwsuite.presentation.ui.session

import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import kotlinx.coroutines.flow.StateFlow

interface SessionViewModel :
    DialogViewModeled<SessionUi, SessionUiAction, SessionUiSingleEvent, SessionFields> {
    val isPasswordValid: StateFlow<Boolean>
    val isLogged: StateFlow<Boolean>

    val username: StateFlow<InputWrapper>
    val pin: StateFlow<InputWrapper>
    val confirmPin: StateFlow<InputWrapper>

    val areSignupInputsValid: StateFlow<Boolean>

    fun setSessionMode(mode: SessionModeType)
}