package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class TerritoriesFields : Focusable {
    TERRITORY_MEMBER,
    TERRITORY_RECEIVING_DATE;

    override fun key(): String {
        return this.name
    }
}
