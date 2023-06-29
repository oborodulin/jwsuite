package com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class MembersListUiAction : UiAction {
    data class Load(val congregationId: UUID? = null, val groupId: UUID? = null) :
        MembersListUiAction()

    data class EditMember(val memberId: UUID) : MembersListUiAction()
    data class DeleteMember(val memberId: UUID) : MembersListUiAction()
}