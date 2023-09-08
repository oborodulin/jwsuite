package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryDetailsUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class EditTerritoryStreet(val territoryId: UUID, val territoryStreetId: UUID? = null) :
        TerritoryDetailsUiAction()
}