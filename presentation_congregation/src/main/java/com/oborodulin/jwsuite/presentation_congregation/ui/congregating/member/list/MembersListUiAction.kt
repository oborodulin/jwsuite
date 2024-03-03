package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class MembersListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(
        val congregationId: UUID? = null, val isService: Boolean = false
    ) : MembersListUiAction()
}