package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class RegionDistrictUiAction : UiAction {
    object Create : RegionDistrictUiAction()
    data class Load(val localityId: UUID) : RegionDistrictUiAction()
    object Save : RegionDistrictUiAction()
}