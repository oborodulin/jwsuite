package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class TerritoryCategoriesListUiSingleEvent : UiSingleEvent {
    data class OpenTerritoryCategoryScreen(val navRoute: String) : TerritoryCategoriesListUiSingleEvent()
}

