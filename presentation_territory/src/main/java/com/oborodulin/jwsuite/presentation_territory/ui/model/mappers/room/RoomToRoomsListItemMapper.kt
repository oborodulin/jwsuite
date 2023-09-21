package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Room
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomsListItem
import java.util.UUID

class RoomToRoomsListItemMapper : Mapper<Room, RoomsListItem> {
    override fun map(input: Room) = RoomsListItem(
        id = input.id ?: UUID.randomUUID(),
        roomNum = input.roomNum,
        isIntercom = input.isIntercom,
        isResidential = input.isResidential,
        isForeignLanguage = input.isForeignLanguage,
        houseFullNum = input.house.houseFullNum,
        roomFullNum = input.roomFullNum,
        territoryFullCardNum = input.territoryFullCardNum,
        info = input.info
    )
}