package com.oborodulin.jwsuite.data.local.db.mappers.territory

data class TerritoryMappers(
    val territoryViewListToTerritoryListMapper: TerritoryViewListToTerritoryListMapper,
    val territoryViewToTerritoryMapper: TerritoryViewToTerritoryMapper,
    val territoryListToTerritoryEntityListMapper: TerritoryListToTerritoryEntityListMapper,
    val territoryToTerritoryEntityMapper: TerritoryToTerritoryEntityMapper,
    val territoryDistrictViewListToTerritoryDistrictListMapper: TerritoryDistrictViewListToTerritoryDistrictListMapper,
    val territoryDistrictViewToTerritoryDistrictMapper: TerritoryDistrictViewToTerritoryDistrictMapper
)
