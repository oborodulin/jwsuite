package com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityDistrictView
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict

class LocalityDistrictViewListToGeoLocalityDistrictsListMapper(mapper: LocalityDistrictViewToGeoLocalityDistrictMapper) :
    ListMapperImpl<LocalityDistrictView, GeoLocalityDistrict>(mapper)