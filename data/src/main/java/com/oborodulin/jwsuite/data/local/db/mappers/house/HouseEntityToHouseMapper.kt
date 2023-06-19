package com.oborodulin.jwsuite.data.local.db.mappers.house

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.domain.model.House

class HouseEntityToHouseMapper : Mapper<HouseEntity, House> {
    override fun map(input: HouseEntity): House {
        val house = House(
            streetId = input.streetsId,
            localityDistrictId = input.localityDistrictsId,
            microdistrictId = input.microdistrictsId,
            territoryId = input.territoriesId,
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
            territoryDesc = input.territoryDesc
        )
        house.id = input.houseId
        return house
    }
}