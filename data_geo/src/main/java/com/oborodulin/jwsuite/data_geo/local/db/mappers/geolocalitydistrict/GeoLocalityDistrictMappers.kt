package com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict

data class GeoLocalityDistrictMappers(
    val localityDistrictViewListToGeoLocalityDistrictsListMapper: LocalityDistrictViewListToGeoLocalityDistrictsListMapper,
    val geoLocalityDistrictViewToGeoLocalityDistrictMapper: GeoLocalityDistrictViewToGeoLocalityDistrictMapper,
    val geoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper: GeoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper,
    val geoLocalityDistrictToGeoLocalityDistrictEntityMapper: GeoLocalityDistrictToGeoLocalityDistrictEntityMapper,
    val geoLocalityDistrictToGeoLocalityDistrictTlEntityMapper: GeoLocalityDistrictToGeoLocalityDistrictTlEntityMapper
)
