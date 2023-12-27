package com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class PartialHousesUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class LoadLocations(
        val congregationId: UUID? = null,
        val isPrivateSector: Boolean = false
    ) : PartialHousesUiAction()

    data object HandOutTerritoriesConfirmation : PartialHousesUiAction()
}