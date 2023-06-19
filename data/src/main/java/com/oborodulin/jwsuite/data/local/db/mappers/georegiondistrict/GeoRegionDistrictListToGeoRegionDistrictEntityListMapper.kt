package com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict

class GeoRegionDistrictListToGeoRegionDistrictEntityListMapper(mapper: GeoRegionDistrictToGeoRegionDistrictEntityMapper) :
    ListMapperImpl<GeoRegionDistrict, GeoRegionDistrictEntity>(mapper)