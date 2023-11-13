package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryCategoriesListItem

interface TerritoryCategoriesListViewModel :
    ListViewModeled<List<TerritoryCategoriesListItem>, TerritoryCategoriesListUiAction, TerritoryCategoriesListUiSingleEvent>