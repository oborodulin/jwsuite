package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class RegionsListUiSingleEvent : UiSingleEvent {
    data class OpenRegionScreen(val navRoute: String) : RegionsListUiSingleEvent()
}

