package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class RegionUiAction : UiAction {
    data class Load(val regionId: UUID? = null) : RegionUiAction()
    object Save : RegionUiAction()
}