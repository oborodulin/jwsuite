package com.oborodulin.jwsuite.data.local.db.mappers.entrance

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.domain.model.Entrance
import java.util.UUID

class EntranceToEntranceEntityMapper : Mapper<Entrance, EntranceEntity> {
    override fun map(input: Entrance) = EntranceEntity(
        entranceId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        entranceNum = input.entranceNum,
        isSecurityEntrance = input.isSecurity,
        isIntercomEntrance = input.isIntercom,
        isResidentialEntrance = input.isResidential,
        entranceFloorsQty = input.floorsQty,
        roomsByEntranceFloor = input.roomsByFloor,
        estEntranceRooms = input.estimatedRooms,
        entranceDesc = input.territoryDesc,
        eTerritoriesId = input.territoryId,
        eHousesId = input.houseId
    )
}