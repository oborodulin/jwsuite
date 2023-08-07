package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.ListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geostreet.GeoStreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data.local.db.views.TerritoryStreetHouseView
import com.oborodulin.jwsuite.domain.model.House
import com.oborodulin.jwsuite.domain.model.TerritoryStreet

class TerritoryStreetHouseViewListToTerritoryStreetsListMapper(
    private val territoryStreetMapper: TerritoryStreetViewToTerritoryStreetMapper,
    private val streetMapper: GeoStreetViewToGeoStreetMapper
) :
    ListMapper<TerritoryStreetHouseView, TerritoryStreet> {
    override fun map(input: List<TerritoryStreetHouseView>): List<TerritoryStreet> {
        val territoryStreets = mutableListOf<TerritoryStreet>()
        input.groupBy({ it.territoryStreet }, { it.house })
            .forEach { (territoryStreetView, houseEntities) ->
                val territoryStreet = territoryStreetMapper.map(territoryStreetView)
                territoryStreet.houses = houseEntities.map {
                    val house = House(
                        street = streetMapper.map(territoryStreetView.geoStreet),
                        localityDistrictId = it.hLocalityDistrictsId,
                        microdistrictId = it.hMicrodistrictsId,
                        territoryId = it.hTerritoriesId,
                        zipCode = it.zipCode,
                        houseNum = it.houseNum,
                        buildingNum = it.buildingNum,
                        isBusiness = it.isBusinessHouse,
                        isSecurity = it.isSecurityHouse,
                        isIntercom = it.isIntercomHouse,
                        isResidential = it.isResidentialHouse,
                        houseEntrancesQty = it.houseEntrancesQty,
                        floorsByEntrance = it.floorsByEntrance,
                        roomsByHouseFloor = it.roomsByHouseFloor,
                        estimatedRooms = it.estHouseRooms,
                        isForeignLanguage = it.isForeignLangHouse,
                        isPrivateSector = it.isHousePrivateSector,
                        buildingType = it.buildingType,
                        houseDesc = it.houseDesc
                    )
                    house.id = it.houseId
                    house
                }
                territoryStreets.add(territoryStreet)
            }
        return territoryStreets
    }
}