package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.jwsuite.data.local.db.mappers.entrance.EntranceEntityListToEntrancesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.floor.FloorEntityListToFloorsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geostreet.GeoStreetViewListToGeoStreetsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.house.HouseViewListToHousesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.room.RoomEntityListToRoomsListMapper

data class TerritoryMappers(
    val territoryViewListToTerritoriesListMapper: TerritoryViewListToTerritoriesListMapper,
    val territoryViewToTerritoryMapper: TerritoryViewToTerritoryMapper,
    val territoriesListToTerritoryEntityListMapper: TerritoriesListToTerritoryEntityListMapper,
    val territoryToTerritoryEntityMapper: TerritoryToTerritoryEntityMapper,
    val territoryDistrictViewListToTerritoryDistrictListMapper: TerritoryDistrictViewListToTerritoryDistrictListMapper,
    val territoryDistrictViewToTerritoryDistrictMapper: TerritoryDistrictViewToTerritoryDistrictMapper,
    val territoryStreetViewListToTerritoryStreetsListMapper: TerritoryStreetViewListToTerritoryStreetsListMapper,
    val geoStreetViewListToGeoStreetsListMapper: GeoStreetViewListToGeoStreetsListMapper,
    val houseViewListToHousesListMapper: HouseViewListToHousesListMapper,
    val entranceEntityListToEntrancesListMapper: EntranceEntityListToEntrancesListMapper,
    val floorEntityListToFloorsListMapper: FloorEntityListToFloorsListMapper,
    val roomEntityListToRoomsListMapper: RoomEntityListToRoomsListMapper,

    val territoriesAtWorkViewToTerritoryMapper: TerritoriesAtWorkViewToTerritoryMapper,
    val territoriesHandOutViewToTerritoryMapper: TerritoriesHandOutViewToTerritoryMapper,
    val territoriesIdleViewToTerritoryMapper: TerritoriesIdleViewToTerritoryMapper
)
