package com.oborodulin.jwsuite.presentation.ui.session

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class SessionUiSingleEvent : UiSingleEvent {
    data class OpenMainScreen(val navRoute: String) : SessionUiSingleEvent()
    data class OpenLoginScreen(val navRoute: String) : SessionUiSingleEvent()
    data class OpenSignupScreen(val navRoute: String) : SessionUiSingleEvent()
}

