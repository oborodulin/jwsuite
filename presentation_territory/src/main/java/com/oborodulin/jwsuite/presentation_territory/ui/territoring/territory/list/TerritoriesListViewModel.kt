package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoriesListItem

interface TerritoriesListViewModel :
    MviViewModeled<List<TerritoriesListItem>, TerritoriesListUiAction, UiSingleEvent> {
}