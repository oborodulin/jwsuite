package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.role.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class RolesListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val memberId: UUID? = null) : RolesListUiAction()
}