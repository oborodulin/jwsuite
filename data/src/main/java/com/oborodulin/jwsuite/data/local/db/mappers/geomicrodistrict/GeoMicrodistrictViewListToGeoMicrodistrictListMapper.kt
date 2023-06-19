package com.oborodulin.jwsuite.data.local.db.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.views.GeoMicrodistrictView
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict

class GeoMicrodistrictViewListToGeoMicrodistrictListMapper(mapper: GeoMicrodistrictViewToGeoMicrodistrictMapper) :
    ListMapperImpl<GeoMicrodistrictView, GeoMicrodistrict>(mapper)