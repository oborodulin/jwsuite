package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class RegionInputEvent(val value: String) : Inputable {
    data class RegionId(val input: String) : RegionInputEvent(input)
    data class RegionDistrictId(val input: String) : RegionInputEvent(input)
    data class RegionCode(val input: String) : RegionInputEvent(input)
    data class RegionType(val input: String) : RegionInputEvent(input)
    data class RegionName(val input: String) : RegionInputEvent(input)
    data class RegionShortName(val input: String) : RegionInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
