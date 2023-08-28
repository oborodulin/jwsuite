package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryDetailsListItem

interface TerritoryDetailsListViewModel :
    MviViewModeled<List<TerritoryDetailsListItem>, TerritoryDetailsListUiAction, UiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}