package com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation_geo.model.RegionDistrictsListItem

interface RegionDistrictsListViewModel :
    MviViewModeled<List<RegionDistrictsListItem>, RegionDistrictsListUiAction, RegionDistrictsListUiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}