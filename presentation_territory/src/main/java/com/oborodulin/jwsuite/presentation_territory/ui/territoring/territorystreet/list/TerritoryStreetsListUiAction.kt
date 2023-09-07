package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryStreetsListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val territoryId: UUID) : TerritoryStreetsListUiAction()
    data class EditTerritoryStreet(val territoryId: UUID, val territoryStreetId: UUID) :
        TerritoryStreetsListUiAction()

    data class DeleteTerritoryStreet(val territoryStreetId: UUID) : TerritoryStreetsListUiAction()
}