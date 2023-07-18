package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TerritoryCategoryUiAction : UiAction {
    data class Load(val territoryCategoryId: UUID? = null) : TerritoryCategoryUiAction()
    object Save : TerritoryCategoryUiAction()
}