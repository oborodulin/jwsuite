package com.oborodulin.jwsuite.presentation.ui.modules.congregating

import com.oborodulin.home.common.ui.state.UiAction

sealed class CongregatingUiAction : UiAction {
    object Init : CongregatingUiAction()
    //data class Load(val payerId: UUID) : AccountingUiAction()
}

