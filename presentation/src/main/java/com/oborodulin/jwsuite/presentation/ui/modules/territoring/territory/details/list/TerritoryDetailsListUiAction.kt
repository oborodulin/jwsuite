package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryDetailsListUiAction : UiAction {
    data class Load(val territoryId: UUID) : TerritoryDetailsListUiAction()
}