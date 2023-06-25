package com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.payer.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class PayersListUiSingleEvent : UiSingleEvent {
    data class OpenPayerScreen(val navRoute: String) : PayersListUiSingleEvent()
}

