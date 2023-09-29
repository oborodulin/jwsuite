package com.oborodulin.jwsuite.presentation_territory.ui.housing

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class HousingUiSingleEvent : UiSingleEvent {
    data class OpenHandOutTerritoriesConfirmationScreen(val navRoute: String) :
        HousingUiSingleEvent()
}

