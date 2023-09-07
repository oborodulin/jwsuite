package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val territoryId: UUID? = null) : TerritoryUiAction()
    data class GetNextTerritoryNum(val congregationId: UUID, val territoryCategoryId: UUID) :
        TerritoryUiAction()

    data object Save : TerritoryUiAction()
    data class EditTerritoryDetails(val territoryId: UUID? = null) : TerritoryUiAction(false)
}