package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class TerritoryRoomFields : Focusable {
    TERRITORY_ROOM_TERRITORY;

    override fun key(): String {
        return this.name
    }
}
