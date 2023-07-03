package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.model.RegionDistrictsListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface RegionDistrictsListViewModel :
    MviViewModeled<List<RegionDistrictsListItem>, RegionDistrictsListUiAction> {
    var primaryObjectData: StateFlow<ArrayList<String>>

    val singleEventFlow: Flow<RegionDistrictsListUiSingleEvent>
    val actionsJobFlow: SharedFlow<Job?>

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
    fun setPrimaryObjectData(value: ArrayList<String>)
}