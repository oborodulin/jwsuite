package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class TerritoryCategoriesListUiSingleEvent : UiSingleEvent {
    data class OpenTerritoryCategoryScreen(val navRoute: String) : TerritoryCategoriesListUiSingleEvent()
}

