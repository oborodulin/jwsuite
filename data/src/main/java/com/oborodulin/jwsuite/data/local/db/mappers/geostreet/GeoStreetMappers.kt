package com.oborodulin.jwsuite.data.local.db.mappers.geostreet

import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryStreetViewListToTerritoryStreetsListMapper

data class GeoStreetMappers(
    val geoStreetViewListToGeoStreetsListMapper: GeoStreetViewListToGeoStreetsListMapper,
    val geoStreetViewToGeoStreetMapper: GeoStreetViewToGeoStreetMapper,
    val geoStreetsListToGeoStreetEntityListMapper: GeoStreetsListToGeoStreetEntityListMapper,
    val geoStreetToGeoStreetEntityMapper: GeoStreetToGeoStreetEntityMapper,
    val geoStreetToGeoStreetTlEntityMapper: GeoStreetToGeoStreetTlEntityMapper,
    val territoryStreetViewListToTerritoryStreetsListMapper: TerritoryStreetViewListToTerritoryStreetsListMapper
)
