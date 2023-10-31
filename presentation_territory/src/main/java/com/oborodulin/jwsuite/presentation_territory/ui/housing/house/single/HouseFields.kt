package com.oborodulin.jwsuite.presentation_territory.ui.housing.house.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class HouseFields : Focusable {
    HOUSE_ID,
    HOUSE_LOCALITY,
    HOUSE_STREET,
    HOUSE_LOCALITY_DISTRICT,
    HOUSE_MICRODISTRICT,
    HOUSE_TERRITORY,
    HOUSE_ZIP_CODE,
    HOUSE_NUM,
    HOUSE_LETTER,
    HOUSE_BUILDING_NUM,
    HOUSE_BUILDING_TYPE,
    HOUSE_IS_BUSINESS,
    HOUSE_IS_SECURITY,
    HOUSE_IS_INTERCOM,
    HOUSE_IS_RESIDENTIAL,
    HOUSE_ENTRANCES_QTY,
    HOUSE_FLOORS_BY_ENTRANCE,
    HOUSE_ROOMS_BY_FLOOR,
    HOUSE_ESTIMATED_ROOMS,
    HOUSE_IS_FOREIGN_LANGUAGE,
    HOUSE_IS_PRIVATE_SECTOR,
    HOUSE_DESC;

    override fun key(): String {
        return this.name
    }
}
