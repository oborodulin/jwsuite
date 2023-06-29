package com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list

import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MembersListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MembersListViewModel {
    var primaryObjectData: StateFlow<ArrayList<String>>

    val uiStateFlow: StateFlow<UiState<List<MembersListItem>>>
    val singleEventFlow: Flow<MembersListUiSingleEvent>
    val actionsJobFlow: SharedFlow<Job?>

    fun submitAction(action: MembersListUiAction): Job?
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
    fun setPrimaryObjectData(value: ArrayList<String>)
}