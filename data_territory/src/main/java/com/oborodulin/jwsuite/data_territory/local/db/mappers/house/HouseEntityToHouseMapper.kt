package com.oborodulin.jwsuite.data_territory.local.db.mappers.house

import android.content.Context
import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict
import com.oborodulin.jwsuite.domain.model.GeoStreet
import com.oborodulin.jwsuite.domain.model.House
import com.oborodulin.jwsuite.domain.model.Territory

class HouseEntityToHouseMapper(private val ctx: Context) : ConstructedMapper<HouseEntity, House> {
    override fun map(input: HouseEntity, vararg properties: Any?): House {
        if (properties.size != 4 ||
            properties[0] !is GeoStreet || (properties[1] != null && properties[1] !is GeoLocalityDistrict) ||
            (properties[2] != null && properties[2] !is GeoMicrodistrict) || (properties[3] != null && properties[3] !is Territory)
        ) throw IllegalArgumentException(
            "HouseEntityToHouseMapper: properties size not equal 4 or properties[0] is not GeoStreet class or properties[1] is not GeoLocalityDistrict class " +
                    "or properties[2] is not GeoMicrodistrict class or properties[3] is not Territory class: size = %d; input.id = %s".format(
                        properties.size, input.houseId
                    )
        )
        val house = House(
            ctx = ctx,
            street = properties[0] as GeoStreet,
            localityDistrict = properties[1] as? GeoLocalityDistrict,
            microdistrict = properties[2] as? GeoMicrodistrict,
            territory = properties[3] as? Territory,
            zipCode = input.zipCode,
            houseNum = input.houseNum,
            houseLetter = input.houseLetter,
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