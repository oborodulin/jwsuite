package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion

data class GeoRegionMappers(
    val regionViewListToGeoRegionsListMapper: RegionViewListToGeoRegionsListMapper,
    val geoRegionViewToGeoRegionMapper: GeoRegionViewToGeoRegionMapper,
    val geoRegionsListToGeoRegionEntityListMapper: GeoRegionsListToGeoRegionEntityListMapper,
    val geoRegionToGeoRegionEntityMapper: GeoRegionToGeoRegionEntityMapper,
    val geoRegionToGeoRegionTlEntityMapper: GeoRegionToGeoRegionTlEntityMapper
)
