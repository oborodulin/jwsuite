package com.oborodulin.jwsuite.data.local.db.mappers.geolocality

data class GeoLocalityMappers(
    val geoLocalityViewListToGeoLocalitiesListMapper: GeoLocalityViewListToGeoLocalitiesListMapper,
    val geoLocalityViewToGeoLocalityMapper: GeoLocalityViewToGeoLocalityMapper,
    val geoLocalitiesListToGeoLocalityEntityListMapper: GeoLocalitiesListToGeoLocalityEntityListMapper,
    val geoLocalityToGeoLocalityEntityMapper: GeoLocalityToGeoLocalityEntityMapper,
    val geoLocalityToGeoLocalityTlEntityMapper: GeoLocalityToGeoLocalityTlEntityMapper
)
