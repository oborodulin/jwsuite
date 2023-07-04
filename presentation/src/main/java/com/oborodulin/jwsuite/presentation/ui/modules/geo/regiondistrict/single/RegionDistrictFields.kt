package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class RegionDistrictFields : Focusable {
    REGION_DISTRICT_ID,
    REGION_DISTRICT_REGION,
    DISTRICT_SHORT_NAME,
    DISTRICT_NAME;

    override fun key(): String {
        return this.name
    }
}
