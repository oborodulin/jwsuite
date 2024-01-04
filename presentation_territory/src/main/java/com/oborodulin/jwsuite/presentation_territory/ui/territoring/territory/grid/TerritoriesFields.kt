package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class TerritoriesFields : Focusable {
    TERRITORY_MEMBER,
    TERRITORY_RECEIVING_DATE,
    TERRITORY_DELIVERY_DATE;

    override fun key() = this.name
}
