package com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict

class GeoMicrodistrictsListToGeoMicrodistrictEntityListMapper(mapper: GeoMicrodistrictToGeoMicrodistrictEntityMapper) :
    ListMapperImpl<GeoMicrodistrict, GeoMicrodistrictEntity>(mapper)