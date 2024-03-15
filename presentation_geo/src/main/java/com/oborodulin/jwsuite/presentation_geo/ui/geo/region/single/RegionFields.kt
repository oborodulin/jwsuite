package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class RegionFields : Focusable {
    REGION_ID,
    REGION_TL_ID,
    REGION_COUNTRY,
    REGION_CODE,
    REGION_TYPE,
    REGION_NAME,
    REGION_GEOCODE,
    REGION_OSM_ID,
    REGION_LATITUDE,
    REGION_LONGITUDE;

    override fun key() = this.name
}
