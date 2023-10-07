package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory

import com.oborodulin.home.common.mapping.ListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.street.TerritoryStreetViewToTerritoryStreetMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetHouseView
import com.oborodulin.jwsuite.data_territory.local.db.views.toKey
import com.oborodulin.jwsuite.domain.model.territory.House
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreet

class TerritoryStreetHouseViewListToTerritoryStreetsListMapper(
    private val territoryStreetMapper: TerritoryStreetViewToTerritoryStreetMapper,
    private val territoryMapper: TerritoryViewToTerritoryMapper
) :
    ListMapper<TerritoryStreetHouseView, TerritoryStreet> {
    override fun map(input: List<TerritoryStreetHouseView>): List<TerritoryStreet> {
        val territoryStreets = mutableListOf<TerritoryStreet>()
        input.groupBy({ it.toKey() }, { it.house })
            .forEach { (key, houseEntities) ->
                val territoryStreet = territoryStreetMapper.map(key.territoryStreet)
                val territory = territoryMapper.map(key.territory)
                territoryStreet.houses = houseEntities.mapNotNull { territoryHouse ->
                    territoryHouse?.let {
                        val house = House(
                            street = territoryStreet.street,
                            localityDistrict = territory.localityDistrict,
                            microdistrict = territory.microdistrict,
                            territory = territory,
                            zipCode = it.zipCode,
                            houseNum = it.houseNum,
                            houseLetter = it.houseLetter,
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
                }
                territoryStreets.add(territoryStreet)
            }
        return territoryStreets
    }
}