package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.LocalitiesListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

interface LocalitiesListViewModel :
    MviViewModeled<List<LocalitiesListItem>, LocalitiesListUiAction, LocalitiesListUiSingleEvent> {

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}