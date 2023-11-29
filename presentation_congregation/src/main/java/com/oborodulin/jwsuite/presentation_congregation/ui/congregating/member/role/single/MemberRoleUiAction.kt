package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class MemberRoleUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val memberRoleId: UUID? = null) : MemberRoleUiAction()
    data object Save : MemberRoleUiAction()
}