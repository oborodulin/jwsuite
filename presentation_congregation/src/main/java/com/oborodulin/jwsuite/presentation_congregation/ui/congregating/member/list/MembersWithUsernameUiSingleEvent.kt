package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class MembersWithUsernameUiSingleEvent : UiSingleEvent {
    data class OpenMemberScreen(val navRoute: String) : MembersWithUsernameUiSingleEvent()
}

