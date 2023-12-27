package com.oborodulin.jwsuite.presentation_territory.ui.reporting.rooms

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class PartialRoomsFields : Focusable {
    PARTIAL_ROOM_HOUSES;

    override fun key(): String {
        return this.name
    }
}
