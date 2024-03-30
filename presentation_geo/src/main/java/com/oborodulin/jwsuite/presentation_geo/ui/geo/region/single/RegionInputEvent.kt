package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class RegionInputEvent(val value: String) : Inputable {
    data class Country(val input: ListItemModel) : RegionInputEvent(input.headline)
    data class RegionCode(val input: String) : RegionInputEvent(input)
    data class RegionType(val input: String) : RegionInputEvent(input)
    data class IsRegionTypePrefix(val input: Boolean) : RegionInputEvent(input.toString())
    data class RegionName(val input: String) : RegionInputEvent(input)

    override fun value() = this.value
}
