package com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictsListItem

interface LocalityDistrictsListViewModel :
    ListViewModeled<List<LocalityDistrictsListItem>, LocalityDistrictsListUiAction, LocalityDistrictsListUiSingleEvent>