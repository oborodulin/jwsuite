package com.oborodulin.jwsuite.data.local.db.mappers.room

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.domain.model.Room
import java.util.UUID

class RoomToRoomEntityMapper : Mapper<Room, RoomEntity> {
    override fun map(input: Room) = RoomEntity(
        roomId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        roomNum = input.roomNum,
        isIntercomRoom = input.isIntercom,
        isResidentialRoom = input.isResidential,
        isForeignLangRoom = input.isForeignLanguage,
        roomDesc = input.territoryDesc,
        rTerritoriesId = input.territoryId,
        rFloorsId = input.floorId,
        rEntrancesId = input.entranceId,
        rHousesId = input.houseId
    )
}