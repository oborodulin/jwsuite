package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details.street.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryStreetsListUiAction : UiAction {
    data class Load(val territoryId: UUID) : TerritoryStreetsListUiAction()
    data class DeleteTerritoryStreet(val territoryId: UUID) : TerritoryStreetsListUiAction()
}