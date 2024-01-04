package com.oborodulin.jwsuite.presentation_territory.ui.housing.room.single

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class RoomFields : Focusable {
    ROOM_ID,
    ROOM_LOCALITY,
    ROOM_LOCALITY_DISTRICT,
    ROOM_MICRODISTRICT,
    ROOM_STREET,
    ROOM_HOUSE,
    ROOM_ENTRANCE,
    ROOM_FLOOR,
    ROOM_TERRITORY,
    ROOM_NUM,
    ROOM_IS_INTERCOM,
    ROOM_IS_RESIDENTIAL,
    ROOM_IS_FOREIGN_LANGUAGE,
    ROOM_DESC;

    override fun key() = this.name
}
