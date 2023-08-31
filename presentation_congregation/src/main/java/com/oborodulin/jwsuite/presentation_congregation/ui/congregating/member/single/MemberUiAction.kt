package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class MemberUiAction : UiAction {
    data class Load(val memberId: UUID? = null) : MemberUiAction()
    data object Save : MemberUiAction()
}