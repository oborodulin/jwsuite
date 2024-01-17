package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity

class GeoMicrodistrictsListToGeoMicrodistrictEntityListMapper(mapper: GeoMicrodistrictToGeoMicrodistrictEntityMapper) :
    ListMapperImpl<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv, GeoMicrodistrictEntity>(mapper)