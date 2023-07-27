package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryLocationsListUiAction : UiAction {
    data class Load(
        val congregationId: UUID? = null,
        val isPrivateSector: Boolean = false
    ) : TerritoryLocationsListUiAction()
}