package com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.MicrodistrictView
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict

class MicrodistrictViewListToGeoMicrodistrictsListMapper(mapper: MicrodistrictViewToGeoMicrodistrictMapper) :
    ListMapperImpl<MicrodistrictView, GeoMicrodistrict>(mapper)