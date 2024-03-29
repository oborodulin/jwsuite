package com.oborodulin.jwsuite.data_territory.local.db.mappers.room

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.domain.model.territory.Room
import java.util.UUID

class RoomToRoomEntityMapper : Mapper<Room, RoomEntity> {
    override fun map(input: Room) = RoomEntity(
        roomId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        roomNum = input.roomNum,
        isIntercomRoom = input.isIntercom,
        isResidentialRoom = input.isResidential,
        isForeignLangRoom = input.isForeignLanguage,
        roomDesc = input.roomDesc,
        rTerritoriesId = input.territory?.id,
        rFloorsId = input.floor?.id,
        rEntrancesId = input.entrance?.id,
        rHousesId = input.house.id!!
    )
}