package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryDetailsListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

interface TerritoryDetailsViewModel :
    MviViewModeled<List<TerritoryDetailsListItem>, TerritoryDetailsUiAction, UiSingleEvent> {
    val actionsJobFlow: SharedFlow<Job?>

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}