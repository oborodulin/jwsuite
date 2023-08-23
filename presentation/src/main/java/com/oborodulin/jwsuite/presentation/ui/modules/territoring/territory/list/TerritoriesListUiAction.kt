package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoriesListUiAction : UiAction {
    data class Load(val congregationId: UUID? = null) : TerritoriesListUiAction()
}