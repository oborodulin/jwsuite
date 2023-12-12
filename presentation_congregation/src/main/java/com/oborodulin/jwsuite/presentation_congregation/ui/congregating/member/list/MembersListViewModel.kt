package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MembersListItem
import kotlinx.coroutines.flow.StateFlow

interface MembersListViewModel :
    ListViewModeled<List<MembersListItem>, MembersListUiAction, MembersListUiSingleEvent> {
    val areInputsValid: StateFlow<Boolean>
}