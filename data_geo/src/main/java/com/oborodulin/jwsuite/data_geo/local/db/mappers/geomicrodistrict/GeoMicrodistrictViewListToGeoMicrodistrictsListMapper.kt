package com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoMicrodistrictView
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict

class GeoMicrodistrictViewListToGeoMicrodistrictsListMapper(mapper: GeoMicrodistrictViewToGeoMicrodistrictMapper) :
    ListMapperImpl<GeoMicrodistrictView, GeoMicrodistrict>(mapper)