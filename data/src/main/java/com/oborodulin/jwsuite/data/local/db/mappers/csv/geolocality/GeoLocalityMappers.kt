package com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocality

data class GeoLocalityMappers(
    val geoLocalityViewListToGeoLocalitiesListMapper: GeoLocalityViewListToGeoLocalitiesListMapper,
    val geoLocalityViewToGeoLocalityMapper: GeoLocalityViewToGeoLocalityMapper,
    val geoLocalitiesListToGeoLocalityEntityListMapper: GeoLocalitiesListToGeoLocalityEntityListMapper,
    val geoLocalityToGeoLocalityEntityMapper: GeoLocalityToGeoLocalityEntityMapper,
    val geoLocalityToGeoLocalityTlEntityMapper: GeoLocalityToGeoLocalityTlEntityMapper
)
