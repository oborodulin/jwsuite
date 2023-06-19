package com.oborodulin.jwsuite.data.local.db.mappers.house

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.domain.model.House
import java.util.UUID

class HouseToHouseEntityMapper : Mapper<House, HouseEntity> {
    override fun map(input: House) = HouseEntity(
        houseId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        zipCode = input.zipCode,
        houseNum = input.houseNum,
        buildingNum = input.buildingNum,
        isBusiness = input.isBusiness,
        isSecurity = input.isSecurity,
        isIntercom = input.isIntercom,
        isResidential = input.isResidential,
        entrancesQty = input.entrancesQty,
        floorsQty = input.floorsQty,
        roomsByFloor = input.roomsByFloor,
        estimatedRooms = input.estimatedRooms,
        isForeignLanguage = input.isForeignLanguage,
        isPrivateSector = input.isPrivateSector,
        isHostel = input.isHostel,
        territoryDesc = input.territoryDesc,
        territoriesId = input.territoryId,
        microdistrictsId = input.microdistrictId,
        localityDistrictsId = input.localityDistrictId,
        streetsId = input.streetId
    )
}