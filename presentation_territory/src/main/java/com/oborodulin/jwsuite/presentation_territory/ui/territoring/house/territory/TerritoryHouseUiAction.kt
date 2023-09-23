package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.territory

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryHouseUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val territoryId: UUID) : TerritoryHouseUiAction()
    data class Save(val houseIds: List<UUID>) : TerritoryHouseUiAction()
}