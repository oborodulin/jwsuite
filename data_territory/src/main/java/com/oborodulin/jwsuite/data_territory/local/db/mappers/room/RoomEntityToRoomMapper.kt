package com.oborodulin.jwsuite.data_territory.local.db.mappers.room

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.domain.model.Room

class RoomEntityToRoomMapper : Mapper<RoomEntity, Room> {
    override fun map(input: RoomEntity): Room {
        val room = Room(
            houseId = input.rHousesId,
            entranceId = input.rEntrancesId,
            floorId = input.rFloorsId,
            territoryId = input.rTerritoriesId,
            roomNum = input.roomNum,
            isIntercom = input.isIntercomRoom,
            isResidential = input.isResidentialRoom,
            isForeignLanguage = input.isForeignLangRoom,
            territoryDesc = input.roomDesc
        )
        room.id = input.roomId
        return room
    }
}