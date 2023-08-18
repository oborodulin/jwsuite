package com.oborodulin.jwsuite.presentation.ui.modules.geo.localitydistrict.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.LocalityDistrictsListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

interface LocalityDistrictsListViewModel :
    MviViewModeled<List<LocalityDistrictsListItem>, LocalityDistrictsListUiAction, LocalityDistrictsListUiSingleEvent> {
    val actionsJobFlow: SharedFlow<Job?>

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}