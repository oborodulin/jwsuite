package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class MembersWithUsernameUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class LoadByCongregation(
        val congregationId: UUID? = null, val isService: Boolean = false
    ) : MembersWithUsernameUiAction()

    data class LoadByGroup(val groupId: UUID? = null, val isService: Boolean = false) :
        MembersWithUsernameUiAction()

    data class EditMember(val memberId: UUID) : MembersWithUsernameUiAction()
    data class DeleteMember(val memberId: UUID) : MembersWithUsernameUiAction()
}