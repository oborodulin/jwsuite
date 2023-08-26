package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorystreet.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryStreetsListItem

interface TerritoryStreetsListViewModel :
    MviViewModeled<List<TerritoryStreetsListItem>, TerritoryStreetsListUiAction, UiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}