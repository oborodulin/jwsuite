package com.oborodulin.jwsuite.presentation.ui.modules.territoring

import com.oborodulin.home.common.ui.state.UiAction

sealed class TerritoringUiAction : UiAction {
    object Init : TerritoringUiAction()
    //data class Load(val payerId: UUID) : AccountingUiAction()
}

