package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryLocationsListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

interface TerritoryLocationsListViewModel :
    MviViewModeled<List<TerritoryLocationsListItem>, TerritoryLocationsListUiAction, TerritoriesGridUiSingleEvent> {
    val actionsJobFlow: SharedFlow<Job?>

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}