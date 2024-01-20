package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv

class GeoRegionDistrictEntityListToGeoRegionDistrictCsvListMapper(mapper: GeoRegionDistrictEntityToGeoRegionDistrictCsvMapper) :
    ListMapperImpl<GeoRegionDistrictEntity, GeoRegionDistrictCsv>(mapper)