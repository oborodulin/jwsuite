package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoriesGridUiAction : UiAction {
    object Load : TerritoriesGridUiAction()
    data class EditCongregation(val congregationId: UUID) : TerritoriesGridUiAction()
    data class DeleteCongregation(val congregationId: UUID) : TerritoriesGridUiAction()
    data class MakeFavoriteCongregation(val congregationId: UUID) : TerritoriesGridUiAction()
}