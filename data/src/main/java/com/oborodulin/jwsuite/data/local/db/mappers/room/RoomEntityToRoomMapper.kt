package com.oborodulin.jwsuite.data.local.db.mappers.room

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.domain.model.Room

class RoomEntityToRoomMapper : Mapper<RoomEntity, Room> {
    override fun map(input: RoomEntity): Room {
        val room = Room(
            houseId = input.housesId,
            entranceId = input.entrancesId,
            floorId = input.floorsId,
            territoryId = input.territoriesId,
            roomNum = input.roomNum,
            isIntercom = input.isIntercom,
            isResidential = input.isResidential,
            isForeignLanguage = input.isForeignLanguage,
            territoryDesc = input.territoryDesc
        )
        room.id = input.roomId
        return room
    }
}