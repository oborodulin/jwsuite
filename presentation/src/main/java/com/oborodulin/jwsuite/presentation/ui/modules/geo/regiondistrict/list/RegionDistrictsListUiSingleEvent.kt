package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class RegionDistrictsListUiSingleEvent : UiSingleEvent {
    data class OpenRegionDistrictScreen(val navRoute: String) : RegionDistrictsListUiSingleEvent()
}

