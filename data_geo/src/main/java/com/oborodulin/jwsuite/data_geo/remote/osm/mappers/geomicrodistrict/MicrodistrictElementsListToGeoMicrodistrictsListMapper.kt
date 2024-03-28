package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.remote.osm.model.microdistrict.MicrodistrictElement
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict

class MicrodistrictElementsListToGeoMicrodistrictsListMapper(mapper: MicrodistrictElementToGeoMicrodistrictMapper) :
    ListMapperImpl<MicrodistrictElement, GeoMicrodistrict>(mapper)