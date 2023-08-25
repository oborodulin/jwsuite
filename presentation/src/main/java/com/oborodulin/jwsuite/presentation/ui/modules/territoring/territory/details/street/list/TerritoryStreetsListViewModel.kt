package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details.street.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryStreetsListItem

interface TerritoryStreetsListViewModel :
    MviViewModeled<List<TerritoryStreetsListItem>, TerritoryStreetsListUiAction, UiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}