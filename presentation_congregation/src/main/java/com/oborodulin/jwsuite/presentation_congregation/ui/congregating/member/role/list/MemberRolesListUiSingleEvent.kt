package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class MemberRolesListUiSingleEvent : UiSingleEvent {
    data class OpenMemberRoleScreen(val navRoute: String) : MemberRolesListUiSingleEvent()
}

