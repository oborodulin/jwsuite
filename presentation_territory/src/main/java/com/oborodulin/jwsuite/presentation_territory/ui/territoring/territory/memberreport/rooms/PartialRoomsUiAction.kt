package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.memberreport.rooms

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class PartialRoomsUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class LoadLocations(
        val congregationId: UUID? = null,
        val isPrivateSector: Boolean = false
    ) : PartialRoomsUiAction()

    data object HandOutTerritoriesConfirmation : PartialRoomsUiAction()
}