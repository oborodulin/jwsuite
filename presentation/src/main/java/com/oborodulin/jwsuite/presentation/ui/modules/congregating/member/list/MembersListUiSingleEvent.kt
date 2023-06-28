package com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class MembersListUiSingleEvent : UiSingleEvent {
    data class OpenPayerScreen(val navRoute: String) : MembersListUiSingleEvent()
}

