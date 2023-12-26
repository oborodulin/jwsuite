package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.memberreport.houses

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class PartialHousesUiSingleEvent : UiSingleEvent {
    data class OpenHandOutTerritoriesConfirmationScreen(val navRoute: String) :
        PartialHousesUiSingleEvent()
}

