package com.oborodulin.jwsuite.presentation_territory.ui.housing

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class HousingFields : Focusable {
    HOUSES_LOCALITY,
    HOUSES_STREET;

    override fun key() = this.name
}
