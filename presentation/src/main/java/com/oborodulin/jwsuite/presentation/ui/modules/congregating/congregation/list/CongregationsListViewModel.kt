package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list

import com.oborodulin.jwsuite.presentation.ui.congregating.model.CongregationListItem
import com.oborodulin.home.common.ui.state.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.ArrayList

interface CongregationsListViewModel {
    var primaryObjectData: StateFlow<ArrayList<String>>

    val uiStateFlow: StateFlow<UiState<List<CongregationListItem>>>
    val singleEventFlow: Flow<CongregationsListUiSingleEvent>
    val actionsJobFlow: SharedFlow<Job?>

    fun submitAction(action: CongregationsListUiAction): Job?
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
    fun setPrimaryObjectData(value: ArrayList<String>)
}