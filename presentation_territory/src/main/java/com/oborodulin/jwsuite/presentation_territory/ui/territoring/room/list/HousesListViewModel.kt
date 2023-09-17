package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem

interface HousesListViewModel :
    MviViewModeled<List<HousesListItem>, HousesListUiAction, UiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}