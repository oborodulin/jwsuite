package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class RegionDistrictUiAction : UiAction {
    data class Load(val regionDistrictId: UUID? = null) : RegionDistrictUiAction()
    object Save : RegionDistrictUiAction()
}