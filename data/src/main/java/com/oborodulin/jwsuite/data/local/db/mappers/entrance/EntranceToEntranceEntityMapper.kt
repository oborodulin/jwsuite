package com.oborodulin.jwsuite.data.local.db.mappers.entrance

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.domain.model.Entrance
import java.util.UUID

class EntranceToEntranceEntityMapper : Mapper<Entrance, EntranceEntity> {
    override fun map(input: Entrance) = EntranceEntity(
        entranceId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        entranceNum = input.entranceNum,
        isSecurity = input.isSecurity,
        isIntercom = input.isIntercom,
        isResidential = input.isResidential,
        floorsQty = input.floorsQty,
        roomsByFloor = input.roomsByFloor,
        estimatedRooms = input.estimatedRooms,
        territoryDesc = input.territoryDesc,
        territoriesId = input.territoryId,
        housesId = input.houseId
    )
}