package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict

class GeoRegionDistrictsListToGeoRegionDistrictEntityListMapper(mapper: GeoRegionDistrictToGeoRegionDistrictEntityMapper) :
    ListMapperImpl<GeoRegionDistrict, GeoRegionDistrictEntity>(mapper)