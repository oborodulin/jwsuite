package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class TerritoryDetailsUiSingleEvent : UiSingleEvent {
    data class OpenTerritoryStreetScreen(val navRoute: String) : TerritoryDetailsUiSingleEvent()
}

