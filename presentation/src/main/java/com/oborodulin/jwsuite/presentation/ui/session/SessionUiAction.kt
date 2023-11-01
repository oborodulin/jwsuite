package com.oborodulin.jwsuite.presentation.ui.session

import com.oborodulin.home.common.ui.state.UiAction

sealed class SessionUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data object Load : SessionUiAction()
    data object Signup : SessionUiAction()
    data object Signout : SessionUiAction()
    data object Login : SessionUiAction()
    data class Logout(val lastDestination: String? = null) : SessionUiAction()
}