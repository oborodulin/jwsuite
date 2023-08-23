package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryCategoriesListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

interface TerritoryCategoriesListViewModel :
    MviViewModeled<List<TerritoryCategoriesListItem>, TerritoryCategoriesListUiAction, TerritoryCategoriesListUiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}