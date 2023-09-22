package com.oborodulin.jwsuite.presentation_territory.ui.territoring.houses

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class HousesFields : Focusable {
    HOUSES_LOCALITY,
    HOUSES_STREET;

    override fun key(): String {
        return this.name
    }
}
