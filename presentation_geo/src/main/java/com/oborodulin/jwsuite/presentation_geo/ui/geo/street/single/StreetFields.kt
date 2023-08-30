package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class StreetFields : Focusable {
    STREET_ID,
    STREET_LOCALITY,
    STREET_LOCALITY_DISTRICT,
    STREET_MICRODISTRICT,
    STREET_ROAD_TYPE,
    STREET_IS_PRIVATE_SECTOR,
    STREET_EST_HOUSES,
    STREET_NAME;

    override fun key(): String {
        return this.name
    }
}
