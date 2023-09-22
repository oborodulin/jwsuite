package com.oborodulin.jwsuite.presentation_territory.ui.territoring.houses

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class HousesUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class LoadLocations(
        val congregationId: UUID? = null,
        val isPrivateSector: Boolean = false
    ) : HousesUiAction()

    data object HandOutTerritoriesConfirmation : HousesUiAction()
}