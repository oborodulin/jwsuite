package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class TerritoryFields : Focusable {
    TERRITORY_ID,
    TERRITORY_CONGREGATION,
    TERRITORY_CATEGORY,
    TERRITORY_LOCALITY,
    TERRITORY_LOCALITY_DISTRICT,
    TERRITORY_MICRODISTRICT,
    TERRITORY_NUM,
    TERRITORY_IS_BUSINESS,
    TERRITORY_IS_GROUP_MINISTRY,
    TERRITORY_IS_ACTIVE,
    TERRITORY_DESC;

    override fun key(): String {
        return this.name
    }
}
