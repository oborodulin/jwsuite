package com.oborodulin.jwsuite.data_territory.local.csv.mappers.house

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.HouseCsv

class HouseEntityToHouseCsvMapper : Mapper<HouseEntity, HouseCsv> {
    override fun map(input: HouseEntity) = HouseCsv(
        houseId = input.houseId,
        zipCode = input.zipCode,
        houseNum = input.houseNum,
        houseLetter = input.houseLetter,
        buildingNum = input.buildingNum,
        buildingType = input.buildingType,
        isBusinessHouse = input.isBusinessHouse,
        isSecurityHouse = input.isSecurityHouse,
        isIntercomHouse = input.isIntercomHouse,
        isResidentialHouse = input.isResidentialHouse,
        houseEntrancesQty = input.houseEntrancesQty,
        floorsByEntrance = input.floorsByEntrance,
        roomsByHouseFloor = input.roomsByHouseFloor,
        estHouseRooms = input.estHouseRooms,
        isForeignLangHouse = input.isForeignLangHouse,
        isHousePrivateSector = input.isHousePrivateSector,
        houseDesc = input.houseDesc,
        houseOsmId = input.houseOsmId,
        latitude = input.coordinates?.latitude,
        longitude = input.coordinates?.longitude,
        hTerritoriesId = input.hTerritoriesId,
        hMicrodistrictsId = input.hMicrodistrictsId,
        hLocalityDistrictsId = input.hLocalityDistrictsId,
        hStreetsId = input.hStreetsId
    )
}