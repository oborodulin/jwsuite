package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationsListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

interface TerritoriesGridViewModel :
    MviViewModeled<List<CongregationsListItem>, TerritoriesGridUiAction, TerritoriesGridUiSingleEvent> {
    val actionsJobFlow: SharedFlow<Job?>

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}