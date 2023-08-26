package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.jwsuite.data.local.db.mappers.entrance.EntranceViewListToEntrancesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.floor.FloorEntityListToFloorsListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetViewListToGeoStreetsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.house.HouseViewListToHousesListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberToMemberEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.room.RoomEntityListToRoomsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.location.TerritoryLocationViewListToTerritoryLocationsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.location.TerritoryLocationViewToTerritoryLocationMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.street.TerritoryStreetToTerritoryStreetEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.street.TerritoryStreetViewListToTerritoryStreetsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.street.TerritoryStreetViewToTerritoryStreetMapper

data class TerritoryMappers(
    val territoryViewListToTerritoriesListMapper: TerritoryViewListToTerritoriesListMapper,
    val territoryViewToTerritoryMapper: TerritoryViewToTerritoryMapper,
    val territoriesListToTerritoryEntityListMapper: TerritoriesListToTerritoryEntityListMapper,
    val territoryToTerritoryEntityMapper: TerritoryToTerritoryEntityMapper,

    val territoryLocationViewListToTerritoryLocationsListMapper: TerritoryLocationViewListToTerritoryLocationsListMapper,
    val territoryLocationViewToTerritoryLocationMapper: TerritoryLocationViewToTerritoryLocationMapper,

    val territoryStreetViewListToTerritoryStreetsListMapper: TerritoryStreetViewListToTerritoryStreetsListMapper,
    val territoryStreetViewToTerritoryStreetMapper: TerritoryStreetViewToTerritoryStreetMapper,
    val territoryStreetToTerritoryStreetEntityMapper: TerritoryStreetToTerritoryStreetEntityMapper,

    val territoryStreetHouseViewListToTerritoryStreetsListMapper: TerritoryStreetHouseViewListToTerritoryStreetsListMapper,
    val geoStreetViewListToGeoStreetsListMapper: GeoStreetViewListToGeoStreetsListMapper,
    val houseViewListToHousesListMapper: HouseViewListToHousesListMapper,
    val entranceViewListToEntrancesListMapper: EntranceViewListToEntrancesListMapper,
    val floorEntityListToFloorsListMapper: FloorEntityListToFloorsListMapper,
    val roomEntityListToRoomsListMapper: RoomEntityListToRoomsListMapper,

    val territoriesAtWorkViewListToTerritoriesListMapper: TerritoriesAtWorkViewListToTerritoriesListMapper,
    val territoriesHandOutViewListToTerritoriesListMapper: TerritoriesHandOutViewListToTerritoriesListMapper,
    val territoriesIdleViewListToTerritoriesListMapper: TerritoriesIdleViewListToTerritoriesListMapper,
    val territoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper: TerritoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper,

    val memberToMemberEntityMapper: MemberToMemberEntityMapper
)
