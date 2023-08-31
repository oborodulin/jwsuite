package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class TerritoryStreetsListUiSingleEvent : UiSingleEvent {
    data class OpenTerritoryStreetScreen(val navRoute: String) : TerritoryStreetsListUiSingleEvent()
}

