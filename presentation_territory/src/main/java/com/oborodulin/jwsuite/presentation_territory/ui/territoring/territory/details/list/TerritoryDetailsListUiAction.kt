package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryDetailsListUiAction : UiAction {
    data class Load(val territoryId: UUID) : TerritoryDetailsListUiAction()
}