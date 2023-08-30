package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class GroupsListUiAction : UiAction {
    data class Load(val congregationId: UUID? = null) : GroupsListUiAction()
    data class EditGroup(val groupId: UUID) : GroupsListUiAction()
    data class DeleteGroup(val groupId: UUID) : GroupsListUiAction()
}