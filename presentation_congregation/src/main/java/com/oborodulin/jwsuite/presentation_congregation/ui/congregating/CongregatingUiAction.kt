package com.oborodulin.jwsuite.presentation_congregation.ui.congregating

import com.oborodulin.home.common.ui.state.UiAction

sealed class CongregatingUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data object Init : CongregatingUiAction()
    //data class Load(val payerId: UUID) : AccountingUiAction()
}

