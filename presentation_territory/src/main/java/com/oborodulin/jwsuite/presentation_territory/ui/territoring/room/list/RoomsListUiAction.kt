package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class RoomsListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val houseId: UUID? = null, val territoryId: UUID? = null) :
        RoomsListUiAction()

    data class LoadForTerritory(val territoryId: UUID) : RoomsListUiAction()
    data class EditRoom(val roomId: UUID) : RoomsListUiAction()
    data class DeleteRoom(val roomId: UUID) : RoomsListUiAction()
    data class EditTerritoryRoom(val territoryId: UUID, val roomId: UUID? = null) :
        RoomsListUiAction()

    data class DeleteTerritoryRoom(val roomId: UUID) : RoomsListUiAction()
}