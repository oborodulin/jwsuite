package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.microdistrict

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem

sealed class StreetLocalityDistrictInputEvent(val value: String) : Inputable {
    data class Street(val input: StreetsListItem) : StreetLocalityDistrictInputEvent(input.headline)

    override fun value(): String {
        return this.value
    }
}
