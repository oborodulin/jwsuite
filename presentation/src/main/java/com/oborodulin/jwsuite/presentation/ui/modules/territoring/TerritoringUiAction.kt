package com.oborodulin.jwsuite.presentation.ui.modules.territoring

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoringUiAction : UiAction {
    data class LoadLocations(
        val congregationId: UUID? = null,
        val isPrivateSector: Boolean = false
    ) : TerritoringUiAction()
}