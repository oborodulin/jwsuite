package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class StreetLocalityDistrictFields : Focusable {
    TERRITORY_HOUSE_TERRITORY,
    TERRITORY_HOUSE_HOUSE;

    override fun key(): String {
        return this.name
    }
}
