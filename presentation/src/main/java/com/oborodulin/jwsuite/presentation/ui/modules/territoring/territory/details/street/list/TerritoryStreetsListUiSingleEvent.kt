package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details.street.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class TerritoryStreetsListUiSingleEvent : UiSingleEvent {
    data class OpenTerritoryStreetScreen(val navRoute: String) : TerritoryStreetsListUiSingleEvent()
}

