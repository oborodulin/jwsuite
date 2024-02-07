package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict

import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.tl.GeoRegionDistrictTlCsvListToGeoRegionDistrictTlEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.tl.GeoRegionDistrictTlEntityListToGeoRegionDistrictTlCsvListMapper

data class GeoRegionDistrictCsvMappers(
    val geoRegionDistrictEntityListToGeoRegionDistrictCsvListMapper: GeoRegionDistrictEntityListToGeoRegionDistrictCsvListMapper,
    val geoRegionDistrictCsvListToGeoRegionDistrictEntityListMapper: GeoRegionDistrictCsvListToGeoRegionDistrictEntityListMapper,
    val geoRegionDistrictTlEntityListToGeoRegionDistrictTlCsvListMapper: GeoRegionDistrictTlEntityListToGeoRegionDistrictTlCsvListMapper,
    val geoRegionDistrictTlCsvListToGeoRegionDistrictTlEntityListMapper: GeoRegionDistrictTlCsvListToGeoRegionDistrictTlEntityListMapper
)