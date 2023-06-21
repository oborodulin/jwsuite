package com.oborodulin.jwsuite.data.local.db.mappers.house

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.domain.model.House

class HouseEntityToHouseMapper : Mapper<HouseEntity, House> {
    override fun map(input: HouseEntity): House {
        val house = House(
            streetId = input.hStreetsId,
            localityDistrictId = input.hLocalityDistrictsId,
            microdistrictId = input.hMicrodistrictsId,
            territoryId = input.hTerritoriesId,
            zipCode = input.zipCode,
            houseNum = input.houseNum,
            buildingNum = input.buildingNum,
            isBusiness = input.isBusinessHouse,
            isSecurity = input.isSecurityHouse,
            isIntercom = input.isIntercomHouse,
            isResidential = input.isResidentialHouse,
            houseEntrancesQty = input.houseEntrancesQty,
            floorsByEntrance = input.floorsByEntrance,
            roomsByHouseFloor = input.roomsByHouseFloor,
            estimatedRooms = input.estHouseRooms,
            isForeignLanguage = input.isForeignLangHouse,
            isPrivateSector = input.isHousePrivateSector,
            buildingType = input.buildingType,
            houseDesc = input.houseDesc
        )
        house.id = input.houseId
        return house
    }
}