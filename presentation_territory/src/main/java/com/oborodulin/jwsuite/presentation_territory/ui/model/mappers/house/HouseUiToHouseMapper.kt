package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.territory.House
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictUiToLocalityDistrictMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictUiToMicrodistrictMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetUiToStreetMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.HouseUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryUiToTerritoryMapper

class HouseUiToHouseMapper(
    private val streetUiMapper: StreetUiToStreetMapper,
    private val localityDistrictUiMapper: LocalityDistrictUiToLocalityDistrictMapper,
    private val microistrictUiMapper: MicrodistrictUiToMicrodistrictMapper,
    private val territoryUiMapper: TerritoryUiToTerritoryMapper
) : Mapper<HouseUi, House> {
    override fun map(input: HouseUi): House {
        val house = House(
            street = streetUiMapper.map(input.street),
            localityDistrict = localityDistrictUiMapper.nullableMap(input.localityDistrict),
            microdistrict = microistrictUiMapper.nullableMap(input.microdistrict),
            territory = territoryUiMapper.nullableMap(input.territory),
            zipCode = input.zipCode,
            houseNum = input.houseNum!!,
            houseLetter = input.houseLetter,
            buildingNum = input.buildingNum,
            buildingType = input.buildingType,
            isBusiness = input.isBusiness,
            isSecurity = input.isSecurity,
            isIntercom = input.isIntercom,
            isResidential = input.isResidential,
            houseEntrancesQty = input.houseEntrancesQty,
            floorsByEntrance = input.floorsByEntrance,
            roomsByHouseFloor = input.roomsByHouseFloor,
            estimatedRooms = input.estimatedRooms,
            isForeignLanguage = input.isForeignLanguage,
            isPrivateSector = input.isPrivateSector,
            houseDesc = input.houseDesc
        )
        house.id = input.id
        return house
    }
}