package com.oborodulin.jwsuite.data.local.db.mappers.geolocality

data class GeoLocalityMappers(
    val geoLocalityViewListToGeoLocalityListMapper: GeoLocalityViewListToGeoLocalityListMapper,
    val geoLocalityViewToGeoLocalityMapper: GeoLocalityViewToGeoLocalityMapper,
    val geoLocalityListToGeoLocalityEntityListMapper: GeoLocalityListToGeoLocalityEntityListMapper,
    val geoLocalityToGeoLocalityEntityMapper: GeoLocalityToGeoLocalityEntityMapper,
    val geoLocalityToGeoLocalityTlEntityMapper: GeoLocalityToGeoLocalityTlEntityMapper
)
