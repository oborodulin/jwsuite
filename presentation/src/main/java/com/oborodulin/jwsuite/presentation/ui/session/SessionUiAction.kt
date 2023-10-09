package com.oborodulin.jwsuite.presentation.ui.session

import com.oborodulin.home.common.ui.state.UiAction

sealed class SessionUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data object Load : SessionUiAction()
    data class Signup(val username: String, val password: String) : SessionUiAction()
    data object Signout : SessionUiAction()
    data class Login(val password: String) : SessionUiAction()
    data object Logout : SessionUiAction()
}