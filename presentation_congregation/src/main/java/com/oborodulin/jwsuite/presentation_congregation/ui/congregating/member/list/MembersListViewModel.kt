package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MembersListItem

interface MembersListViewModel :
    MviViewModeled<List<MembersListItem>, MembersListUiAction, MembersListUiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}