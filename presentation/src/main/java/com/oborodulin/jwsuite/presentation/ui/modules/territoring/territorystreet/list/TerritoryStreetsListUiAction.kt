package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorystreet.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryStreetsListUiAction : UiAction {
    data class Load(val territoryId: UUID) : TerritoryStreetsListUiAction()
    data class EditTerritoryStreet(val territoryStreetId: UUID) : TerritoryStreetsListUiAction()
    data class DeleteTerritoryStreet(val territoryStreetId: UUID) : TerritoryStreetsListUiAction()
}