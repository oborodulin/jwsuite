package com.oborodulin.jwsuite.presentation_geo.ui.geo.country.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.jwsuite.presentation_geo.ui.model.CountriesListItem

interface CountriesListViewModel :
    ListViewModeled<List<CountriesListItem>, CountriesListUiAction, CountriesListUiSingleEvent>