package com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.views.GeoLocalityDistrictView
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict

class GeoLocalityDistrictViewListToGeoLocalityDistrictListMapper(mapper: GeoLocalityDistrictViewToGeoLocalityDistrictMapper) :
    ListMapperImpl<GeoLocalityDistrictView, GeoLocalityDistrict>(mapper)