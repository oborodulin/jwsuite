package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class RegionDistrictInputEvent(val value: String) : Inputable {
    data class Region(val input: ListItemModel) : RegionDistrictInputEvent(input.headline)
    data class RegionDistrict(val input: ListItemModel) : RegionDistrictInputEvent(input.headline)
    data class RegionDistrictCode(val input: String) : RegionDistrictInputEvent(input)
    data class RegionDistrictType(val input: String) : RegionDistrictInputEvent(input)
    data class RegionDistrictName(val input: String) : RegionDistrictInputEvent(input)
    data class RegionDistrictShortName(val input: String) : RegionDistrictInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
