package com.oborodulin.jwsuite.presentation_territory.ui.territoring.setting

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class TerritorySettingFields : Focusable {
    TERRITORY_PROCESSING_PERIOD,
    TERRITORY_AT_HAND_PERIOD,
    TERRITORY_IDLE_PERIOD,
    TERRITORY_ROOMS_LIMIT,
    TERRITORY_MAX_ROOMS;

    override fun key() = this.name
}
