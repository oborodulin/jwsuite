package com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionDistrictsListItem

interface RegionDistrictsListViewModel :
    ListViewModeled<List<RegionDistrictsListItem>, RegionDistrictsListUiAction, RegionDistrictsListUiSingleEvent>