package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class RegionDistrictInputEvent(val value: String) : Inputable {
    data class RegionId(val input: String) : RegionDistrictInputEvent(input)
    data class RegionDistrictDistrictId(val input: String) : RegionDistrictInputEvent(input)
    data class RegionDistrictCode(val input: String) : RegionDistrictInputEvent(input)
    data class RegionDistrictType(val input: String) : RegionDistrictInputEvent(input)
    data class RegionDistrictName(val input: String) : RegionDistrictInputEvent(input)
    data class RegionDistrictShortName(val input: String) : RegionDistrictInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
