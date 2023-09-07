package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryCategoriesListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data object Load : TerritoryCategoriesListUiAction()
    data class EditTerritoryCategory(val territoryCategoryId: UUID) :
        TerritoryCategoriesListUiAction()

    data class DeleteTerritoryCategory(val territoryCategoryId: UUID) :
        TerritoryCategoriesListUiAction()
}