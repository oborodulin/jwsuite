package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class MemberUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val memberId: UUID? = null) : MemberUiAction()
    data object Save : MemberUiAction()
}