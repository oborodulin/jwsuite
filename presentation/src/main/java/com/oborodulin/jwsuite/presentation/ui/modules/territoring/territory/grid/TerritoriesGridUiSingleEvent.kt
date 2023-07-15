package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class TerritoriesGridUiSingleEvent : UiSingleEvent {
    data class OpenCongregationScreen(val navRoute: String) : TerritoriesGridUiSingleEvent()
}

