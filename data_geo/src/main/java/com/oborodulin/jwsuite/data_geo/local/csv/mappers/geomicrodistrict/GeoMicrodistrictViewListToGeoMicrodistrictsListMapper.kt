package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoMicrodistrictView

class GeoMicrodistrictViewListToGeoMicrodistrictsListMapper(mapper: GeoMicrodistrictViewToGeoMicrodistrictMapper) :
    ListMapperImpl<GeoMicrodistrictView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv>(mapper)