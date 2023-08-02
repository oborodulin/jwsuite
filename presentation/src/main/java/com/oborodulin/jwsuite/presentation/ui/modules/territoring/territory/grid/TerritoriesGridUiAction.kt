package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import com.oborodulin.jwsuite.domain.util.TerritoryProcessType
import java.util.UUID

sealed class TerritoriesGridUiAction : UiAction {
    data class Load(
        val congregationId: UUID? = null,
        val territoryProcessType: TerritoryProcessType,
        val territoryLocationType: TerritoryLocationType,
        val locationId: UUID? = null,
        val isPrivateSector: Boolean = false
    ) : TerritoriesGridUiAction()

    data class EditTerritory(val territoryId: UUID) : TerritoriesGridUiAction()
    data class DeleteTerritory(val territoryId: UUID) : TerritoriesGridUiAction()
    object HandOutConfirmation : TerritoriesGridUiAction()
    object HandOut : TerritoriesGridUiAction()
    object Process : TerritoriesGridUiAction()
}