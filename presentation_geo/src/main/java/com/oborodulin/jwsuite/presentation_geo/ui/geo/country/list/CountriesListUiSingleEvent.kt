package com.oborodulin.jwsuite.presentation_geo.ui.geo.country.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class CountriesListUiSingleEvent : UiSingleEvent {
    data class OpenCountryScreen(val navRoute: String) : CountriesListUiSingleEvent()
}

