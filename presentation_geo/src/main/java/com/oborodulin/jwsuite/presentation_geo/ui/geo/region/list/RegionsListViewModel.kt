package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionsListItem

interface RegionsListViewModel :
    MviViewModeled<List<RegionsListItem>, RegionsListUiAction, RegionsListUiSingleEvent> {

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)

}