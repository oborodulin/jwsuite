package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem

interface HousesListViewModel :
    ListViewModeled<List<HousesListItem>, HousesListUiAction, UiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}