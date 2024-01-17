package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity

class GeoRegionDistrictsListToGeoRegionDistrictEntityListMapper(mapper: GeoRegionDistrictToGeoRegionDistrictEntityMapper) :
    ListMapperImpl<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv, GeoRegionDistrictEntity>(mapper)