package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class GroupUiAction : UiAction {
    data class Load(val groupId: UUID? = null) : GroupUiAction()
    object Save : GroupUiAction()
}