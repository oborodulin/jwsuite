package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import kotlinx.coroutines.flow.StateFlow

interface HousesListViewModel :
    MviViewModeled<List<HousesListItem>, HousesListUiAction, UiSingleEvent> {
    val checkedListItems: StateFlow<List<HousesListItem>>
    val areListItemsChecked: StateFlow<Boolean>
    fun observeCheckedListItems()
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}