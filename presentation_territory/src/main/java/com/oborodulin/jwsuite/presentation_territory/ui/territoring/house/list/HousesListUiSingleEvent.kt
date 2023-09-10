package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class HousesListUiSingleEvent : UiSingleEvent {
    data class OpenTerritoryStreetScreen(val navRoute: String) : HousesListUiSingleEvent()
}

