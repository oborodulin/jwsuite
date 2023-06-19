package com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict

class GeoLocalityDistrictListToGeoLocalityDistrictEntityListMapper(mapper: GeoLocalityDistrictToGeoLocalityDistrictEntityMapper) :
    ListMapperImpl<GeoLocalityDistrict, GeoLocalityDistrictEntity>(mapper)