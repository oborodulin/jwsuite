package com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class LocalityDistrictFields : Focusable {
    LOCALITY_DISTRICT_ID,
    LOCALITY_DISTRICT_TL_ID,
    LOCALITY_DISTRICT_LOCALITY,
    LOCALITY_DISTRICT_SHORT_NAME,
    LOCALITY_DISTRICT_NAME;

    override fun key() = this.name
}
