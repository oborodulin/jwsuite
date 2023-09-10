package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class HouseFields : Focusable {
    TERRITORY_STREET_ID,
    TERRITORY_STREET_TERRITORY,
    TERRITORY_STREET_STREET,
    TERRITORY_STREET_IS_PRIVATE_SECTOR,
    TERRITORY_STREET_IS_EVEN_SIDE,
    TERRITORY_STREET_EST_HOUSES;

    override fun key(): String {
        return this.name
    }
}
