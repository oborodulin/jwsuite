package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details.street.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryStreetUiAction : UiAction {
    data class Load(val localityId: UUID? = null) : TerritoryStreetUiAction()
    object Save : TerritoryStreetUiAction()
}