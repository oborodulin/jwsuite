package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.microdistrict

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class StreetMicrodistrictFields : Focusable {
    STREET_MICRODISTRICT_STREET;

    override fun key(): String {
        return this.name
    }
}
