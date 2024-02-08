package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory

import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberToMemberEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance.EntranceViewListToEntrancesListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.floor.FloorViewListToFloorsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseViewListToHousesListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.room.RoomViewListToRoomsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.location.TerritoryLocationViewListToTerritoryLocationsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.location.TerritoryLocationViewToTerritoryLocationMapper

data class TerritoryMappers(
    val territoryViewListToTerritoriesListMapper: TerritoryViewListToTerritoriesListMapper,
    val territoryViewToTerritoryMapper: TerritoryViewToTerritoryMapper,
    val territoriesListToTerritoryEntityListMapper: TerritoriesListToTerritoryEntityListMapper,
    val territoryToTerritoryEntityMapper: TerritoryToTerritoryEntityMapper,

    val territoryLocationViewListToTerritoryLocationsListMapper: TerritoryLocationViewListToTerritoryLocationsListMapper,
    val territoryLocationViewToTerritoryLocationMapper: TerritoryLocationViewToTerritoryLocationMapper,

    val territoryStreetHouseViewListToTerritoryStreetsListMapper: TerritoryStreetHouseViewListToTerritoryStreetsListMapper,
    val houseViewListToHousesListMapper: HouseViewListToHousesListMapper,
    val entranceViewListToEntrancesListMapper: EntranceViewListToEntrancesListMapper,
    val floorViewListToFloorsListMapper: FloorViewListToFloorsListMapper,
    val roomViewListToRoomsListMapper: RoomViewListToRoomsListMapper,

    val territoriesAtWorkViewListToTerritoriesListMapper: TerritoriesAtWorkViewListToTerritoriesListMapper,
    val territoriesHandOutViewListToTerritoriesListMapper: TerritoriesHandOutViewListToTerritoriesListMapper,
    val territoriesIdleViewListToTerritoriesListMapper: TerritoriesIdleViewListToTerritoriesListMapper,

    val territoryTotalViewToTerritoryTotalsMapper: TerritoryTotalViewToTerritoryTotalsMapper,

    val memberToMemberEntityMapper: MemberToMemberEntityMapper
)
