package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryDetailsUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class EditTerritoryStreet(val territoryId: UUID, val territoryStreetId: UUID? = null) :
        TerritoryDetailsUiAction()

    data class EditTerritoryHouse(val territoryId: UUID, val houseId: UUID? = null) :
        TerritoryDetailsUiAction()

    data class EditTerritoryEntrance(val territoryId: UUID, val entranceId: UUID? = null) :
        TerritoryDetailsUiAction()

    data class EditTerritoryFloor(val territoryId: UUID, val floorId: UUID? = null) :
        TerritoryDetailsUiAction()

    data class EditTerritoryRoom(val territoryId: UUID, val roomId: UUID? = null) :
        TerritoryDetailsUiAction()
}