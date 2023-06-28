package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list

import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationsListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface CongregationsListViewModel {
    var primaryObjectData: StateFlow<ArrayList<String>>

    val uiStateFlow: StateFlow<UiState<List<CongregationsListItem>>>
    val singleEventFlow: Flow<CongregationsListUiSingleEvent>
    val actionsJobFlow: SharedFlow<Job?>

    fun submitAction(action: CongregationsListUiAction): Job?
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
    fun setPrimaryObjectData(value: ArrayList<String>)
}