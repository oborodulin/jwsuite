package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality

data class GeoLocalityMappers(
    val geoLocalityViewListToGeoLocalitiesListMapper: com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityViewListToGeoLocalitiesListMapper,
    val geoLocalityViewToGeoLocalityMapper: com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityViewToGeoLocalityMapper,
    val geoLocalitiesListToGeoLocalityEntityListMapper: com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalitiesListToGeoLocalityEntityListMapper,
    val geoLocalityToGeoLocalityEntityMapper: com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityToGeoLocalityEntityMapper,
    val geoLocalityToGeoLocalityTlEntityMapper: com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityToGeoLocalityTlEntityMapper
)
