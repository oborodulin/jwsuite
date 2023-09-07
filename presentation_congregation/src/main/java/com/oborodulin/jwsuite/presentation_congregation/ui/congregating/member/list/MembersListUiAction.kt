package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class MembersListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class LoadByCongregation(val congregationId: UUID? = null) : MembersListUiAction()
    data class LoadByGroup(val groupId: UUID? = null) : MembersListUiAction()
    data class EditMember(val memberId: UUID) : MembersListUiAction()
    data class DeleteMember(val memberId: UUID) : MembersListUiAction()
}