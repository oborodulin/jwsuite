package com.oborodulin.jwsuite.data.local.db.mappers.floor

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.domain.model.Floor
import java.util.UUID

class FloorToFloorEntityMapper : Mapper<Floor, FloorEntity> {
    override fun map(input: Floor) = FloorEntity(
        floorId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        floorNum = input.floorNum,
        isSecurity = input.isSecurity,
        isIntercom = input.isIntercom,
        isResidential = input.isResidential,
        roomsByFloor = input.roomsByFloor,
        estimatedRooms = input.estimatedRooms,
        territoryDesc = input.territoryDesc,
        territoriesId = input.territoryId,
        entrancesId = input.entranceId,
        housesId = input.houseId
    )
}