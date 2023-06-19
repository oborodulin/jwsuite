package com.oborodulin.jwsuite.data.local.db.mappers.georegion

data class GeoRegionMappers(
    val geoRegionViewListToGeoRegionListMapper: GeoRegionViewListToGeoRegionListMapper,
    val geoRegionViewToGeoRegionMapper: GeoRegionViewToGeoRegionMapper,
    val geoRegionListToGeoRegionEntityListMapper: GeoRegionListToGeoRegionEntityListMapper,
    val geoRegionToGeoRegionEntityMapper: GeoRegionToGeoRegionEntityMapper,
    val geoRegionToGeoRegionTlEntityMapper: GeoRegionToGeoRegionTlEntityMapper
)
