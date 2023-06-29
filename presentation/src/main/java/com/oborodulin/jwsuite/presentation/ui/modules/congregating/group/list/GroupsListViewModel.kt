package com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.list

import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.GroupsListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface GroupsListViewModel {
    var primaryObjectData: StateFlow<ArrayList<String>>

    val uiStateFlow: StateFlow<UiState<List<GroupsListItem>>>
    val singleEventFlow: Flow<GroupsListUiSingleEvent>
    val actionsJobFlow: SharedFlow<Job?>

    fun submitAction(action: GroupsListUiAction): Job?
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
    fun setPrimaryObjectData(value: ArrayList<String>)
}