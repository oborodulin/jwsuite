package com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class RegionDistrictUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val regionDistrictId: UUID? = null) : RegionDistrictUiAction()
    object Save : RegionDistrictUiAction()
}