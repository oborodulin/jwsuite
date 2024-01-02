package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid

import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.jwsuite.domain.types.TerritoryLocationType
import com.oborodulin.jwsuite.domain.types.TerritoryProcessType
import java.util.UUID

sealed class TerritoriesGridUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(
        val congregationId: UUID? = null,
        val territoryProcessType: TerritoryProcessType,
        val territoryLocationType: TerritoryLocationType,
        val locationId: UUID? = null,
        val isPrivateSector: Boolean = false
    ) : TerritoriesGridUiAction()

    data class EditTerritory(val territoryId: UUID) : TerritoriesGridUiAction()
    data class DeleteTerritory(val territoryId: UUID) : TerritoriesGridUiAction()

    // HandOut:
    data object HandOutInitConfirmation : TerritoriesGridUiAction(false)
    data object HandOutConfirmation : TerritoriesGridUiAction(false)
    data object HandOut : TerritoriesGridUiAction()

    // Process:
    data object ProcessInitConfirmation : TerritoriesGridUiAction(false)
    data object ProcessConfirmation : TerritoriesGridUiAction(false)
    data object Process : TerritoriesGridUiAction()

    // Report:
    data class ReportStreets(val territoryId: UUID) : TerritoriesGridUiAction()
    data class ReportHouses(val territoryId: UUID) : TerritoriesGridUiAction()

    data class ReportRooms(val territoryId: UUID) : TerritoriesGridUiAction()
}