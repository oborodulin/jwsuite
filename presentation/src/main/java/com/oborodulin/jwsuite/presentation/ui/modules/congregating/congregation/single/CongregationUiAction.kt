package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class CongregationUiAction : UiAction {
    object Create : CongregationUiAction()
    data class Load(val congregationId: UUID) : CongregationUiAction()
    object Save : CongregationUiAction()
}