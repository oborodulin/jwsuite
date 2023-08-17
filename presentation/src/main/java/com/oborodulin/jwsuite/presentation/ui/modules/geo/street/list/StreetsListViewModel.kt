package com.oborodulin.jwsuite.presentation.ui.modules.geo.street.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.StreetsListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

interface StreetsListViewModel :
    MviViewModeled<List<StreetsListItem>, StreetsListUiAction, StreetsListUiSingleEvent> {
    val actionsJobFlow: SharedFlow<Job?>

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}