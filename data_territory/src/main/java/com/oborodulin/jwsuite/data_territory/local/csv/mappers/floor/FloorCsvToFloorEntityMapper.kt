package com.oborodulin.jwsuite.data_territory.local.csv.mappers.floor

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.FloorCsv

class FloorCsvToFloorEntityMapper : Mapper<FloorCsv, FloorEntity> {
    override fun map(input: FloorCsv) = FloorEntity(
        floorId = input.floorId,
        floorNum = input.floorNum,
        isSecurityFloor = input.isSecurityFloor,
        isIntercomFloor = input.isIntercomFloor,
        isResidentialFloor = input.isResidentialFloor,
        roomsByFloor = input.roomsByFloor,
        estFloorRooms = input.estFloorRooms,
        floorDesc = input.floorDesc,
        fTerritoriesId = input.fTerritoriesId,
        fEntrancesId = input.fEntrancesId,
        fHousesId = input.fHousesId
    )
}