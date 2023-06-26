package com.oborodulin.jwsuite.data.local.db.mappers.house

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.geostreet.GeoStreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data.local.db.views.HouseView
import com.oborodulin.jwsuite.domain.model.House

class HouseViewToHouseMapper(private val streetMapper: GeoStreetViewToGeoStreetMapper) :
    Mapper<HouseView, House> {
    override fun map(input: HouseView): House {
        val house = House(
            street = streetMapper.map(input.street),
            localityDistrictId = input.house.hLocalityDistrictsId,
            microdistrictId = input.house.hMicrodistrictsId,
            territoryId = input.house.hTerritoriesId,
            zipCode = input.house.zipCode,
            houseNum = input.house.houseNum,
            buildingNum = input.house.buildingNum,
            isBusiness = input.house.isBusinessHouse,
            isSecurity = input.house.isSecurityHouse,
            isIntercom = input.house.isIntercomHouse,
            isResidential = input.house.isResidentialHouse,
            houseEntrancesQty = input.house.houseEntrancesQty,
            floorsByEntrance = input.house.floorsByEntrance,
            roomsByHouseFloor = input.house.roomsByHouseFloor,
            estimatedRooms = input.house.estHouseRooms,
            isForeignLanguage = input.house.isForeignLangHouse,
            isPrivateSector = input.house.isHousePrivateSector,
            buildingType = input.house.buildingType,
            houseDesc = input.house.houseDesc
        )
        house.id = input.house.houseId
        return house
    }
}