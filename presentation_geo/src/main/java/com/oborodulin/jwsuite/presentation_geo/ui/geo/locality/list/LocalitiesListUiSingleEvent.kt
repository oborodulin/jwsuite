package com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class LocalitiesListUiSingleEvent : UiSingleEvent {
    data class OpenLocalityScreen(val navRoute: String) : com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list.LocalitiesListUiSingleEvent()
}

