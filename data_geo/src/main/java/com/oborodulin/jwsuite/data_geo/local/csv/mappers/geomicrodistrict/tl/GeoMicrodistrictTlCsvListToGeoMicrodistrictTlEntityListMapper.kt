package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict.tl

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictTlCsv

class GeoMicrodistrictTlCsvListToGeoMicrodistrictTlEntityListMapper(mapper: GeoMicrodistrictTlCsvToGeoMicrodistrictTlEntityMapper) :
    ListMapperImpl<GeoMicrodistrictTlCsv, GeoMicrodistrictTlEntity>(mapper)