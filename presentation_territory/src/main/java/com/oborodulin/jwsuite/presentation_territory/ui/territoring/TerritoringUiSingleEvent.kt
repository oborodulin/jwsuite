package com.oborodulin.jwsuite.presentation_territory.ui.territoring

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class TerritoringUiSingleEvent : UiSingleEvent {
    data class OpenHandOutConfirmationScreen(val navRoute: String) :
        TerritoringUiSingleEvent()
}

