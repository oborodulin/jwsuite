package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionsListItem

interface RegionsListViewModel :
    ListViewModeled<List<RegionsListItem>, RegionsListUiAction, RegionsListUiSingleEvent> {

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)

}