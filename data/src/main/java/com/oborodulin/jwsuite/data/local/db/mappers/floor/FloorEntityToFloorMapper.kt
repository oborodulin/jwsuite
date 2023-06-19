package com.oborodulin.jwsuite.data.local.db.mappers.floor

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.domain.model.Floor

class FloorEntityToFloorMapper : Mapper<FloorEntity, Floor> {
    override fun map(input: FloorEntity): Floor {
        val floor = Floor(
            houseId = input.housesId,
            entranceId = input.entrancesId,
            territoryId = input.territoriesId,
            floorNum = input.floorNum,
            isSecurity = input.isSecurity,
            isIntercom = input.isIntercom,
            isResidential = input.isResidential,
            roomsByFloor = input.roomsByFloor,
            estimatedRooms = input.estimatedRooms,
            territoryDesc = input.territoryDesc
        )
        floor.id = input.floorId
        return floor
    }
}