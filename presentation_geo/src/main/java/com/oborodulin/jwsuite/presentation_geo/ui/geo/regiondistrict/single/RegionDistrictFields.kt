package com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class RegionDistrictFields : Focusable {
    REGION_DISTRICT_ID,
    REGION_DISTRICT_TL_ID,
    REGION_DISTRICT_COUNTRY,
    REGION_DISTRICT_REGION,
    DISTRICT_SHORT_NAME,
    DISTRICT_NAME;

    override fun key() =  this.name
}
