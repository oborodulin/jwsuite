package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryDetailsUiAction : UiAction {
    data class Load(val territoryId: UUID) : TerritoryDetailsUiAction()
}