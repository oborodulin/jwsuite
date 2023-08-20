package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryUiAction : UiAction {
    data class Load(val territoryId: UUID? = null) : TerritoryUiAction()
    data object Save : TerritoryUiAction()
}