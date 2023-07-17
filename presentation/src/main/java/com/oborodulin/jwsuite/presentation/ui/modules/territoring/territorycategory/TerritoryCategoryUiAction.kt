package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryCategoryUiAction : UiAction {
    data class Load(val regionId: UUID? = null) : TerritoryCategoryUiAction()
    object Save : TerritoryCategoryUiAction()
}