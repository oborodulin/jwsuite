package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class StreetLocalityDistrictInputEvent(val value: String) : Inputable {
    data class Territory(val input: ListItemModel) : StreetLocalityDistrictInputEvent(input.headline)
    data class House(val input: ListItemModel) : StreetLocalityDistrictInputEvent(input.headline)

    override fun value(): String {
        return this.value
    }
}
