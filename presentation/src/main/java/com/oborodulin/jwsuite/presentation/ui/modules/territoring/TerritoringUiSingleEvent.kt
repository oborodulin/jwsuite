package com.oborodulin.jwsuite.presentation.ui.modules.territoring

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class TerritoringUiSingleEvent : UiSingleEvent {
    data class OpenPayerScreen(val navRoute: String) : TerritoringUiSingleEvent()
}

