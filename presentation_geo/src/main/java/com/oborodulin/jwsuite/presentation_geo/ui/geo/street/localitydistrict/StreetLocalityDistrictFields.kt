package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class StreetLocalityDistrictFields : Focusable {
    STREET_LOCALITY_DISTRICT_STREET;

    override fun key(): String {
        return this.name
    }
}
