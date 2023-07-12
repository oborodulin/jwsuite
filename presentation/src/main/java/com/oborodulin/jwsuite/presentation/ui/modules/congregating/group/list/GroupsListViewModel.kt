package com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.GroupsListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

interface GroupsListViewModel :
    MviViewModeled<List<GroupsListItem>, GroupsListUiAction, GroupsListUiSingleEvent> {
    val actionsJobFlow: SharedFlow<Job?>

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}