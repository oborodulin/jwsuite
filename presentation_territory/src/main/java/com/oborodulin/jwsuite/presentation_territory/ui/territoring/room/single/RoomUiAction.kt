package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class RoomUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val roomId: UUID? = null) : RoomUiAction()
    data object Save : RoomUiAction()
}