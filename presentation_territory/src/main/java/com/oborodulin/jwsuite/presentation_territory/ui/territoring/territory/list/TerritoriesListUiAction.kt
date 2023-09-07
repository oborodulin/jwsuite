package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoriesListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val congregationId: UUID? = null) : TerritoriesListUiAction()
}