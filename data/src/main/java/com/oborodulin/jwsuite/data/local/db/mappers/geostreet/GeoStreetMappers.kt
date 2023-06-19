package com.oborodulin.jwsuite.data.local.db.mappers.geostreet

data class GeoStreetMappers(
    val geoStreetViewListToGeoStreetListMapper: GeoStreetViewListToGeoStreetListMapper,
    val geoStreetViewToGeoStreetMapper: GeoStreetViewToGeoStreetMapper,
    val geoStreetListToGeoStreetEntityListMapper: GeoStreetListToGeoStreetEntityListMapper,
    val geoStreetToGeoStreetEntityMapper: GeoStreetToGeoStreetEntityMapper,
    val geoStreetToGeoStreetTlEntityMapper: GeoStreetToGeoStreetTlEntityMapper
)
