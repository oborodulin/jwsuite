package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class TerritoryUiSingleEvent : UiSingleEvent {
    data class OpenTerritoryDetailsScreen(val navRoute: String) : TerritoryUiSingleEvent()
}

