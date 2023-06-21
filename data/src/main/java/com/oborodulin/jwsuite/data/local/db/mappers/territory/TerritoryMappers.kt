package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.jwsuite.data.local.db.mappers.entrance.EntranceEntityListToEntranceListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.floor.FloorEntityListToFloorListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geostreet.GeoStreetViewListToGeoStreetListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.house.HouseEntityListToHouseListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.room.RoomEntityListToRoomListMapper

data class TerritoryMappers(
    val territoryViewListToTerritoryListMapper: TerritoryViewListToTerritoryListMapper,
    val territoryViewToTerritoryMapper: TerritoryViewToTerritoryMapper,
    val territoryListToTerritoryEntityListMapper: TerritoryListToTerritoryEntityListMapper,
    val territoryToTerritoryEntityMapper: TerritoryToTerritoryEntityMapper,
    val territoryDistrictViewListToTerritoryDistrictListMapper: TerritoryDistrictViewListToTerritoryDistrictListMapper,
    val territoryDistrictViewToTerritoryDistrictMapper: TerritoryDistrictViewToTerritoryDistrictMapper,
    val territoryStreetViewListToTerritoryStreetListMapper: TerritoryStreetViewListToTerritoryStreetListMapper,
    val geoStreetViewListToGeoStreetListMapper: GeoStreetViewListToGeoStreetListMapper,
    val houseEntityListToHouseListMapper: HouseEntityListToHouseListMapper,
    val entranceEntityListToEntranceListMapper: EntranceEntityListToEntranceListMapper,
    val floorEntityListToFloorListMapper: FloorEntityListToFloorListMapper,
    val roomEntityListToRoomListMapper: RoomEntityListToRoomListMapper
)
