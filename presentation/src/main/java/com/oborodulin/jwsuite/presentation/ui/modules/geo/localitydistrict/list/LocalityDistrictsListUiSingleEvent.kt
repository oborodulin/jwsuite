package com.oborodulin.jwsuite.presentation.ui.modules.geo.localitydistrict.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class LocalityDistrictsListUiSingleEvent : UiSingleEvent {
    data class OpenLocalityDistrictScreen(val navRoute: String) : LocalityDistrictsListUiSingleEvent()
}

