package com.oborodulin.jwsuite.data.local.db.mappers.floor

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.domain.model.Floor

class FloorEntityToFloorMapper : Mapper<FloorEntity, Floor> {
    override fun map(input: FloorEntity): Floor {
        val floor = Floor(
            houseId = input.fHousesId,
            entranceId = input.fEntrancesId,
            territoryId = input.fTerritoriesId,
            floorNum = input.floorNum,
            isSecurity = input.isSecurityFloor,
            isIntercom = input.isIntercomFloor,
            isResidential = input.isResidentialFloor,
            roomsByFloor = input.roomsByFloor,
            estimatedRooms = input.estFloorRooms,
            territoryDesc = input.floorDesc
        )
        floor.id = input.floorId
        return floor
    }
}