package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryDetailsListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val territoryId: UUID) : TerritoryDetailsListUiAction()
}