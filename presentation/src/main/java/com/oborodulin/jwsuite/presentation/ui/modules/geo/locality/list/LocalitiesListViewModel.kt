package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.model.LocalitiesListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface LocalitiesListViewModel : MviViewModeled<List<LocalitiesListItem>> {
    var primaryObjectData: StateFlow<ArrayList<String>>

    val singleEventFlow: Flow<LocalitiesListUiSingleEvent>
    val actionsJobFlow: SharedFlow<Job?>

    fun submitAction(action: LocalitiesListUiAction): Job?
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
    fun setPrimaryObjectData(value: ArrayList<String>)
}