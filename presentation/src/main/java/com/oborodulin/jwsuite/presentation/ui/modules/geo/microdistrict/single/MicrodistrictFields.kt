package com.oborodulin.jwsuite.presentation.ui.modules.geo.microdistrict.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class MicrodistrictFields : Focusable {
    MICRODISTRICT_ID,
    MICRODISTRICT_LOCALITY,
    MICRODISTRICT_LOCALITY_DISTRICT,
    LOCALITY_CODE,
    MICRODISTRICT_SHORT_NAME,
    MICRODISTRICT_TYPE,
    MICRODISTRICT_NAME;

    override fun key(): String {
        return this.name
    }
}
