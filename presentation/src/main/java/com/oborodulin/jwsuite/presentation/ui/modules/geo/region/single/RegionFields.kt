package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class RegionFields : Focusable {
    REGION_ID,
    REGION_CODE,
    REGION_NAME;

    override fun key(): String {
        return this.name
    }
}
