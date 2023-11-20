package com.oborodulin.jwsuite.presentation.ui.appsetting

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class AppSettingFields : Focusable {
    TERRITORY_PROCESSING_PERIOD,
    TERRITORY_AT_HAND_PERIOD,
    TERRITORY_IDLE_PERIOD,
    TERRITORY_ROOMS_LIMIT,
    TERRITORY_MAX_ROOMS;

    override fun key(): String {
        return this.name
    }
}
