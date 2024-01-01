package com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class ReportHousesFields : Focusable {
    PARTIAL_HOUSES_TERRITORY_STREET;

    override fun key(): String {
        return this.name
    }
}
