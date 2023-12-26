package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.memberreport.houses

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class PartialHousesFields : Focusable {
    PARTIAL_HOUSES_STREET;

    override fun key(): String {
        return this.name
    }
}
