package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion

import com.oborodulin.jwsuite.data.local.db.mappers.csv.georegion.tl.GeoRegionTlCsvListToGeoRegionTlEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.georegion.tl.GeoRegionTlEntityListToGeoRegionTlCsvListMapper

data class GeoRegionCsvMappers(
    val geoRegionEntityListToGeoRegionCsvListMapper: GeoRegionEntityListToGeoRegionCsvListMapper,
    val geoRegionCsvListToGeoRegionEntityListMapper: GeoRegionCsvListToGeoRegionEntityListMapper,
    val geoRegionTlEntityListToGeoRegionTlCsvListMapper: GeoRegionTlEntityListToGeoRegionTlCsvListMapper,
    val geoRegionTlCsvListToGeoRegionTlEntityListMapper: GeoRegionTlCsvListToGeoRegionTlEntityListMapper
)
