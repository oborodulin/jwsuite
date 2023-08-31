package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryCategoriesListItem

interface TerritoryCategoriesListViewModel :
    MviViewModeled<List<TerritoryCategoriesListItem>, TerritoryCategoriesListUiAction, TerritoryCategoriesListUiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}