package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class MembersListUiSingleEvent : UiSingleEvent {
    data class OpenMemberScreen(val navRoute: String) : MembersListUiSingleEvent()
}

