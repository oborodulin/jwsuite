package com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class RegionDistrictInputEvent(val value: String) : Inputable {
    data class Region(val input: ListItemModel) : RegionDistrictInputEvent(input.headline)
    data class DistrictShortName(val input: String) : RegionDistrictInputEvent(input)
    data class DistrictName(val input: String) : RegionDistrictInputEvent(input)

    override fun value() = this.value
}
