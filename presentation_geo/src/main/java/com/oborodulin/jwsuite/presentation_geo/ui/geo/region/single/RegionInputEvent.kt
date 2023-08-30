package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class RegionInputEvent(val value: String) : Inputable {
    data class RegionCode(val input: String) : RegionInputEvent(input)
    data class RegionName(val input: String) : RegionInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
