package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list

import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MembersWithUsernameUi
import kotlinx.coroutines.flow.StateFlow

interface MembersWithUsernameViewModel :
    MviViewModeled<MembersWithUsernameUi, MembersWithUsernameUiAction, MembersWithUsernameUiSingleEvent> {
    val areSingleSelected: StateFlow<Boolean>
    fun singleSelectItem(selectedItem: ListItemModel)
    fun singleSelectedItem(): ListItemModel?
}