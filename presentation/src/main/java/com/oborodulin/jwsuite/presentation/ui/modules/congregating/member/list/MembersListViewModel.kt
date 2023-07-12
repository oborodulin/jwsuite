package com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MembersListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

interface MembersListViewModel :
    MviViewModeled<List<MembersListItem>, MembersListUiAction, MembersListUiSingleEvent> {
    val actionsJobFlow: SharedFlow<Job?>

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}