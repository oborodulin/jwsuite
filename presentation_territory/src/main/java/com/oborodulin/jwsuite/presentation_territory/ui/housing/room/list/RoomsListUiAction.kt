package com.oborodulin.jwsuite.presentation_territory.ui.housing.room.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class RoomsListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val houseId: UUID? = null, val territoryId: UUID? = null) :
        RoomsListUiAction()

    data class EditRoom(val roomId: UUID) : RoomsListUiAction()
    data class DeleteRoom(val roomId: UUID) : RoomsListUiAction()
    data class DeleteTerritoryRoom(val roomId: UUID) : RoomsListUiAction()
}