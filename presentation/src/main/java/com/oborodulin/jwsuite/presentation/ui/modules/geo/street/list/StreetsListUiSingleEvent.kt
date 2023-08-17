package com.oborodulin.jwsuite.presentation.ui.modules.geo.street.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class StreetsListUiSingleEvent : UiSingleEvent {
    data class OpenStreetScreen(val navRoute: String) : StreetsListUiSingleEvent()
}

