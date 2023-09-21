package com.oborodulin.jwsuite.presentation_geo.ui.geo

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class GeoUiSingleEvent : UiSingleEvent {
    data class OpenPayerScreen(val navRoute: String) : GeoUiSingleEvent()
}

