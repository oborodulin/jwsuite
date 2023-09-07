package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class RegionUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val regionId: UUID? = null) : RegionUiAction()
    object Save : RegionUiAction()
}