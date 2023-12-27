package com.oborodulin.jwsuite.presentation_territory.ui.housing.house.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class HousesListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(
        val streetId: UUID? = null,
        val territoryId: UUID? = null,
        val territoryStreetId: UUID? = null
    ) : HousesListUiAction()

    data class EditHouse(val houseId: UUID) : HousesListUiAction()
    data class DeleteHouse(val houseId: UUID) : HousesListUiAction()
    data class DeleteTerritoryHouse(val houseId: UUID) : HousesListUiAction()
}