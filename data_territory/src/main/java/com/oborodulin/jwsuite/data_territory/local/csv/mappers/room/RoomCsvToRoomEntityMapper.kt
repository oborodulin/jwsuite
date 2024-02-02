package com.oborodulin.jwsuite.data_territory.local.csv.mappers.room

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.RoomCsv

class RoomCsvToRoomEntityMapper : Mapper<RoomCsv, RoomEntity> {
    override fun map(input: RoomCsv) = RoomEntity(
        roomId = input.roomId,
        roomNum = input.roomNum,
        isIntercomRoom = input.isIntercomRoom,
        isResidentialRoom = input.isResidentialRoom,
        isForeignLangRoom = input.isForeignLangRoom,
        roomDesc = input.roomDesc,
        rTerritoriesId = input.rTerritoriesId,
        rFloorsId = input.rFloorsId,
        rEntrancesId = input.rEntrancesId,
        rHousesId = input.rHousesId
    )
}