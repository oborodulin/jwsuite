package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict

data class GeoRegionDistrictMappers(
    val regionDistrictViewListToGeoRegionDistrictsListMapper: RegionDistrictViewListToGeoRegionDistrictsListMapper,
    val geoRegionDistrictViewToGeoRegionDistrictMapper: GeoRegionDistrictViewToGeoRegionDistrictMapper,
    val geoRegionDistrictsListToGeoRegionDistrictEntityListMapper: GeoRegionDistrictsListToGeoRegionDistrictEntityListMapper,
    val geoRegionDistrictToGeoRegionDistrictEntityMapper: GeoRegionDistrictToGeoRegionDistrictEntityMapper,
    val geoRegionDistrictToGeoRegionDistrictTlEntityMapper: GeoRegionDistrictToGeoRegionDistrictTlEntityMapper
)