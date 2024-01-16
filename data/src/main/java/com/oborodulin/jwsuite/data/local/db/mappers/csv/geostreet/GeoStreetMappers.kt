package com.oborodulin.jwsuite.data.local.db.mappers.csv.geostreet

data class GeoStreetMappers(
    val geoStreetViewListToGeoStreetsListMapper: GeoStreetViewListToGeoStreetsListMapper,
    val geoStreetViewToGeoStreetMapper: GeoStreetViewToGeoStreetMapper,
    val geoStreetsListToGeoStreetEntityListMapper: GeoStreetsListToGeoStreetEntityListMapper,
    val geoStreetToGeoStreetEntityMapper: GeoStreetToGeoStreetEntityMapper,
    val geoStreetToGeoStreetTlEntityMapper: GeoStreetToGeoStreetTlEntityMapper
)