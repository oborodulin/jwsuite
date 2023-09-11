package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.territory

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class TerritoryHouseFields : Focusable {
    TERRITORY_HOUSE_TERRITORY,
    TERRITORY_HOUSE_HOUSE;

    override fun key(): String {
        return this.name
    }
}
