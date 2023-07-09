package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class RegionDistrictsListUiAction : UiAction {
    data class Load(val regionId: UUID? = null) : RegionDistrictsListUiAction()
    data class EditRegionDistrict(val regionDistrictId: UUID) : RegionDistrictsListUiAction()
    data class DeleteRegionDistrict(val regionDistrictId: UUID) : RegionDistrictsListUiAction()
}