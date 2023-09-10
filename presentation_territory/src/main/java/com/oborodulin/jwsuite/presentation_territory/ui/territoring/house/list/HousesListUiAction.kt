package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class HousesListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class LoadByStreet(val streetId: UUID) : HousesListUiAction()
    data class EditHouse(val houseId: UUID) : HousesListUiAction()
    data class DeleteHouse(val houseId: UUID) : HousesListUiAction()
    data class LoadByTerritory(val streetId: UUID) : HousesListUiAction()
    data class EditTerritoryHouse(val territoryId: UUID, val houseId: UUID) :
        HousesListUiAction()

    data class DeleteTerritoryHouse(val territoryId: UUID, val houseId: UUID) : HousesListUiAction()
}