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
        isBusinessHouse = input.isBusiness,
        isSecurityHouse = input.isSecurity,
        isIntercomHouse = input.isIntercom,
        isResidentialHouse = input.isResidential,
        houseEntrancesQty = input.houseEntrancesQty,
        floorsByEntrance = input.floorsByEntrance,
        roomsByHouseFloor = input.roomsByHouseFloor,
        estHouseRooms = input.estimatedRooms,
        isForeignLangHouse = input.isForeignLanguage,
        isHousePrivateSector = input.isPrivateSector,
        buildingType = input.buildingType,
        houseDesc = input.houseDesc,
        hTerritoriesId = input.territoryId,
        hMicrodistrictsId = input.microdistrictId,
        hLocalityDistrictsId = input.localityDistrictId,
        hStreetsId = input.street.id!!
    )
}