package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupsListItem

interface GroupsListViewModel :
    ListViewModeled<List<GroupsListItem>, GroupsListUiAction, GroupsListUiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}