package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class MemberRolesListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val memberId: UUID) : MemberRolesListUiAction()
    data class EditMemberRole(val memberRoleId: UUID) : MemberRolesListUiAction()
    data class DeleteMemberRole(val memberRoleId: UUID) : MemberRolesListUiAction()
}