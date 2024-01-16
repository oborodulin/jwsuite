package com.oborodulin.jwsuite.data.local.db.mappers.csv.georegion

data class GeoRegionMappers(
    val geoRegionViewListToGeoRegionsListMapper: GeoRegionViewListToGeoRegionsListMapper,
    val geoRegionViewToGeoRegionMapper: GeoRegionViewToGeoRegionMapper,
    val geoRegionsListToGeoRegionEntityListMapper: GeoRegionsListToGeoRegionEntityListMapper,
    val geoRegionToGeoRegionEntityMapper: GeoRegionToGeoRegionEntityMapper,
    val geoRegionToGeoRegionTlEntityMapper: GeoRegionToGeoRegionTlEntityMapper
)
