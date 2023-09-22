package com.oborodulin.jwsuite.presentation_territory.ui.territoring.houses

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class HousesUiSingleEvent : UiSingleEvent {
    data class OpenHandOutTerritoriesConfirmationScreen(val navRoute: String) :
        HousesUiSingleEvent()
}

