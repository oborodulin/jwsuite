package com.oborodulin.jwsuite.data.local.db.mappers.entrance

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.domain.model.Entrance

class EntranceEntityToEntranceMapper : Mapper<EntranceEntity, Entrance> {
    override fun map(input: EntranceEntity): Entrance {
        val entrance = Entrance(
            houseId = input.housesId,
            territoryId = input.territoriesId,
            entranceNum = input.entranceNum,
            isSecurity = input.isSecurity,
            isIntercom = input.isIntercom,
            isResidential = input.isResidential,
            floorsQty = input.floorsQty,
            roomsByFloor = input.roomsByFloor,
            estimatedRooms = input.estimatedRooms,
            territoryDesc = input.territoryDesc,
        )
        entrance.id = input.entranceId
        return entrance
    }
}