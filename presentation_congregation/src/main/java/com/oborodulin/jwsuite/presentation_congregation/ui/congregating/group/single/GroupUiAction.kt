package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class GroupUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val groupId: UUID? = null) : GroupUiAction()
    data class GetNextGroupNum(val congregationId: UUID) : GroupUiAction()
    data object Save : GroupUiAction()
}