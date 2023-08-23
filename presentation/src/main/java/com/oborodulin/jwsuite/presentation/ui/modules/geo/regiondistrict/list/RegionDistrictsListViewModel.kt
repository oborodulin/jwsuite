package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.RegionDistrictsListItem

interface RegionDistrictsListViewModel :
    MviViewModeled<List<RegionDistrictsListItem>, RegionDistrictsListUiAction, RegionDistrictsListUiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}