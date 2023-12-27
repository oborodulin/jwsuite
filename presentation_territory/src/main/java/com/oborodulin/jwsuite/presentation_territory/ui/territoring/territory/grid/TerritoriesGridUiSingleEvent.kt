package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class TerritoriesGridUiSingleEvent : UiSingleEvent {
    data class OpenTerritoryScreen(val navRoute: String) : TerritoriesGridUiSingleEvent()
    data class OpenHandOutConfirmationScreen(val navRoute: String) :
        TerritoriesGridUiSingleEvent()
    data class OpenProcessConfirmationScreen(val navRoute: String) :
        TerritoriesGridUiSingleEvent()
}

