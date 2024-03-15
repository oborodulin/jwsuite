package com.oborodulin.jwsuite.presentation_geo.ui.geo.country.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class CountryFields : Focusable {
    COUNTRY_ID,
    COUNTRY_TL_ID,
    COUNTRY_CODE,
    COUNTRY_NAME,
    COUNTRY_GEOCODE,
    COUNTRY_OSM_ID,
    COUNTRY_LATITUDE,
    COUNTRY_LONGITUDE;

    override fun key() = this.name
}
