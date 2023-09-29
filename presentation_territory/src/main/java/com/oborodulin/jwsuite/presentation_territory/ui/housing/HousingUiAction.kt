package com.oborodulin.jwsuite.presentation_territory.ui.housing

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class HousingUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class LoadLocations(
        val congregationId: UUID? = null,
        val isPrivateSector: Boolean = false
    ) : HousingUiAction()

    data object HandOutTerritoriesConfirmation : HousingUiAction()
}