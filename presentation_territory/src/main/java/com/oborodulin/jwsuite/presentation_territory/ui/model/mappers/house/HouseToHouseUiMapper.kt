package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.territory.House
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictToLocalityDistrictUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictToMicrodistrictUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetToStreetUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.HouseUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryToTerritoryUiMapper

class HouseToHouseUiMapper(
    private val streetMapper: StreetToStreetUiMapper,
    private val localityDistrictMapper: LocalityDistrictToLocalityDistrictUiMapper,
    private val microistrictMapper: MicrodistrictToMicrodistrictUiMapper,
    private val territoryMapper: TerritoryToTerritoryUiMapper
) : Mapper<House, HouseUi> {
    override fun map(input: House): HouseUi {
        val houseUi = HouseUi(
            street = streetMapper.map(input.street),
            localityDistrict = localityDistrictMapper.nullableMap(input.localityDistrict),
            microdistrict = microistrictMapper.nullableMap(input.microdistrict),
            territory = territoryMapper.nullableMap(input.territory),
            zipCode = input.zipCode,
            houseNum = input.houseNum,
            houseLetter = input.houseLetter,
            buildingNum = input.buildingNum,
            houseFullNum = input.houseFullNum,
            buildingType = input.buildingType,
            isBusiness = input.isBusiness,
            isSecurity = input.isSecurity,
            isIntercom = input.isIntercom,
            isResidential = input.isResidential,
            houseEntrancesQty = input.houseEntrancesQty,
            floorsByEntrance = input.floorsByEntrance,
            roomsByHouseFloor = input.roomsByHouseFloor,
            estimatedRooms = input.estimatedRooms,
            calculatedRooms = input.calculatedRooms,
            isForeignLanguage = input.isForeignLanguage,
            isPrivateSector = input.isPrivateSector,
            houseDesc = input.houseDesc
        )
        houseUi.id = input.id
        return houseUi
    }
}