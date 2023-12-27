package com.oborodulin.jwsuite.presentation_territory.ui.reporting.rooms

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class PartialRoomsUiSingleEvent : UiSingleEvent {
    data class OpenHandOutTerritoriesConfirmationScreen(val navRoute: String) :
        PartialRoomsUiSingleEvent()
}

