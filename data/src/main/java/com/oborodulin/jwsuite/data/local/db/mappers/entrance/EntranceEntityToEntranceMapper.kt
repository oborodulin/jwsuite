package com.oborodulin.jwsuite.data.local.db.mappers.entrance

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.domain.model.Entrance

class EntranceEntityToEntranceMapper : Mapper<EntranceEntity, Entrance> {
    override fun map(input: EntranceEntity): Entrance {
        val entrance = Entrance(
            houseId = input.eHousesId,
            territoryId = input.eTerritoriesId,
            entranceNum = input.entranceNum,
            isSecurity = input.isSecurityEntrance,
            isIntercom = input.isIntercomEntrance,
            isResidential = input.isResidentialEntrance,
            floorsQty = input.entranceFloorsQty,
            roomsByFloor = input.roomsByEntranceFloor,
            estimatedRooms = input.estEntranceRooms,
            territoryDesc = input.entranceDesc,
        )
        entrance.id = input.entranceId
        return entrance
    }
}