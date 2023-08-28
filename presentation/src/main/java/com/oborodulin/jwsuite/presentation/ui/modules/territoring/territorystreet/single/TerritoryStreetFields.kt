package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorystreet.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class TerritoryStreetFields : Focusable {
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