package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryCategoriesListUiAction : UiAction {
    object Load : TerritoryCategoriesListUiAction()
    data class EditTerritoryCategory(val territoryCategoryId: UUID) :
        TerritoryCategoriesListUiAction()

    data class DeleteTerritoryCategory(val territoryCategoryId: UUID) :
        TerritoryCategoriesListUiAction()
}