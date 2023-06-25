package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class LocalityFields : Focusable {
    LOCALITY_ID,
    REGION_ID,
    REGION_DISTRICT_ID,
    LOCALITY_CODE,
    LOCALITY_TYPE,
    LOCALITY_NAME,
    LOCALITY_SHORT_NAME;

    override fun key(): String {
        return this.name
    }
}
