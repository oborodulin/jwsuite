package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.territory

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryRoomUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val territoryId: UUID) : TerritoryRoomUiAction()
    data class Save(val roomIds: List<UUID>) : TerritoryRoomUiAction()
}