package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class TerritoriesGridUiSingleEvent : UiSingleEvent {
    data class OpenTerritoryScreen(val navRoute: String) : TerritoriesGridUiSingleEvent()
    data class OpenHandOutTerritoriesConfirmationScreen(val navRoute: String) :
        TerritoriesGridUiSingleEvent()
}

