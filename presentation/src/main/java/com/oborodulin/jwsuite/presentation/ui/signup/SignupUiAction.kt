package com.oborodulin.jwsuite.presentation.ui.signup

import com.oborodulin.home.common.ui.state.UiAction

sealed class SignupUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Signup(val username: String, val password: String) : SignupUiAction()
}