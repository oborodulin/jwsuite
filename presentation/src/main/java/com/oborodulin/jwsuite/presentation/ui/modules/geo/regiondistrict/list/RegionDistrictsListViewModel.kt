package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.RegionDistrictsListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

interface RegionDistrictsListViewModel :
    MviViewModeled<List<RegionDistrictsListItem>, RegionDistrictsListUiAction, RegionDistrictsListUiSingleEvent> {
    val actionsJobFlow: SharedFlow<Job?>

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}