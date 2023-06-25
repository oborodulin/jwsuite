package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list

import com.oborodulin.jwsuite.presentation.ui.congregating.model.CongregationListItem
import com.oborodulin.home.common.ui.state.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.ArrayList

interface RegionsListViewModel {
    var primaryObjectData: StateFlow<ArrayList<String>>

    val uiStateFlow: StateFlow<UiState<List<CongregationListItem>>>
    val singleEventFlow: Flow<RegionsListUiSingleEvent>
    val actionsJobFlow: SharedFlow<Job?>

    fun submitAction(action: RegionsListUiAction): Job?
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
    fun setPrimaryObjectData(value: ArrayList<String>)
}