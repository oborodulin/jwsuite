package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomsListItem

interface RoomsListViewModel :
    ListViewModeled<List<RoomsListItem>, RoomsListUiAction, UiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}