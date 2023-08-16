package com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityDistrictView
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict

class GeoLocalityDistrictViewListToGeoLocalityDistrictsListMapper(mapper: GeoLocalityDistrictViewToGeoLocalityDistrictMapper) :
    ListMapperImpl<GeoLocalityDistrictView, GeoLocalityDistrict>(mapper)