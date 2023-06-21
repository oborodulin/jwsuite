package com.oborodulin.jwsuite.data.local.db.mappers.floor

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.domain.model.Floor
import java.util.UUID

class FloorToFloorEntityMapper : Mapper<Floor, FloorEntity> {
    override fun map(input: Floor) = FloorEntity(
        floorId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        floorNum = input.floorNum,
        isSecurityFloor = input.isSecurity,
        isIntercomFloor = input.isIntercom,
        isResidentialFloor = input.isResidential,
        roomsByFloor = input.roomsByFloor,
        estFloorRooms = input.estimatedRooms,
        floorDesc = input.territoryDesc,
        fTerritoriesId = input.territoryId,
        fEntrancesId = input.entranceId,
        fHousesId = input.houseId
    )
}