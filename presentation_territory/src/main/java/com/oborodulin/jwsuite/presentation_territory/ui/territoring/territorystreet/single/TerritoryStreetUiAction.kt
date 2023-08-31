package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryStreetUiAction : UiAction {
    data class Load(val territoryId: UUID? = null, val territoryStreetId: UUID? = null) :
        TerritoryStreetUiAction()

    data object Save : TerritoryStreetUiAction()
}