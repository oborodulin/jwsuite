package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class CongregationUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val congregationId: UUID? = null) : CongregationUiAction()
    data object Save : CongregationUiAction()
}