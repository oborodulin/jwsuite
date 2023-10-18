package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoriesListItem

interface TerritoriesListViewModel :
    ListViewModeled<List<TerritoriesListItem>, TerritoriesListUiAction, UiSingleEvent> {
}