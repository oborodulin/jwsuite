package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.RegionsListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

interface RegionsListViewModel :
    MviViewModeled<List<RegionsListItem>, RegionsListUiAction, RegionsListUiSingleEvent> {
    val actionsJobFlow: SharedFlow<Job?>

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)

}