package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomsListItem
import kotlinx.coroutines.flow.StateFlow

interface RoomsListViewModel :
    MviViewModeled<List<RoomsListItem>, RoomsListUiAction, UiSingleEvent> {
    val checkedListItems: StateFlow<List<RoomsListItem>>
    val areListItemsChecked: StateFlow<Boolean>
    fun observeCheckedListItems()
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}