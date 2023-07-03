package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class RegionUiAction : UiAction {
    object Create : RegionUiAction()
    data class Load(val regionId: UUID) : RegionUiAction()
    object Save : RegionUiAction()
}