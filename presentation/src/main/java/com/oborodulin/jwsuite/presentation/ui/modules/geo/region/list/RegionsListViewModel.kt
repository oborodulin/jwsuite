package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.model.RegionsListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface RegionsListViewModel : MviViewModeled<List<RegionsListItem>, RegionsListUiAction> {
    var primaryObjectData: StateFlow<ArrayList<String>>

    val singleEventFlow: Flow<RegionsListUiSingleEvent>
    val actionsJobFlow: SharedFlow<Job?>

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
    fun setPrimaryObjectData(value: ArrayList<String>)
}