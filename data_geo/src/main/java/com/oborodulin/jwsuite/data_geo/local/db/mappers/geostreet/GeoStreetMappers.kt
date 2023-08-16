package com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet

data class GeoStreetMappers(
    val geoStreetViewListToGeoStreetsListMapper: GeoStreetViewListToGeoStreetsListMapper,
    val geoStreetViewToGeoStreetMapper: GeoStreetViewToGeoStreetMapper,
    val geoStreetsListToGeoStreetEntityListMapper: GeoStreetsListToGeoStreetEntityListMapper,
    val geoStreetToGeoStreetEntityMapper: GeoStreetToGeoStreetEntityMapper,
    val geoStreetToGeoStreetTlEntityMapper: GeoStreetToGeoStreetTlEntityMapper
)
