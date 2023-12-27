package com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class PartialHousesUiSingleEvent : UiSingleEvent {
    data class OpenHouseProcessScreen(val navRoute: String) : PartialHousesUiSingleEvent()
}

