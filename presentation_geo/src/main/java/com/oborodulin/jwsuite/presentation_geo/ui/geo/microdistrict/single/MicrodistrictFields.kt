package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class MicrodistrictFields : Focusable {
    MICRODISTRICT_ID,
    MICRODISTRICT_LOCALITY,
    MICRODISTRICT_LOCALITY_DISTRICT,
    LOCALITY_CODE,
    MICRODISTRICT_SHORT_NAME,
    MICRODISTRICT_TYPE,
    MICRODISTRICT_NAME;

    override fun key() = this.name
}
