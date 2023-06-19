package com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.views.GeoRegionDistrictView
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict

class GeoRegionDistrictViewListToGeoRegionDistrictListMapper(mapper: GeoRegionDistrictViewToGeoRegionDistrictMapper) :
    ListMapperImpl<GeoRegionDistrictView, GeoRegionDistrict>(mapper)