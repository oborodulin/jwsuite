package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryDetailsListItem

interface TerritoryDetailsListViewModel :
    ListViewModeled<List<TerritoryDetailsListItem>, TerritoryDetailsListUiAction, UiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}