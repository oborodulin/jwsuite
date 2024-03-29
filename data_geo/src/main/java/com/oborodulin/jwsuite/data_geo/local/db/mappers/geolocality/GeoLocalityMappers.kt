package com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality

data class GeoLocalityMappers(
    val localityViewListToGeoLocalitiesListMapper: LocalityViewListToGeoLocalitiesListMapper,
    val geoLocalityViewToGeoLocalityMapper: GeoLocalityViewToGeoLocalityMapper,
    val geoLocalitiesListToGeoLocalityEntityListMapper: GeoLocalitiesListToGeoLocalityEntityListMapper,
    val geoLocalityToGeoLocalityEntityMapper: GeoLocalityToGeoLocalityEntityMapper,
    val geoLocalityToGeoLocalityTlEntityMapper: GeoLocalityToGeoLocalityTlEntityMapper
)
