package com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class LocalityFields : Focusable {
    LOCALITY_ID,
    LOCALITY_TL_ID,
    LOCALITY_COUNTRY,
    LOCALITY_REGION,
    LOCALITY_REGION_DISTRICT,
    LOCALITY_CODE,
    LOCALITY_SHORT_NAME,
    LOCALITY_TYPE,
    LOCALITY_NAME;

    override fun key() = this.name
}
