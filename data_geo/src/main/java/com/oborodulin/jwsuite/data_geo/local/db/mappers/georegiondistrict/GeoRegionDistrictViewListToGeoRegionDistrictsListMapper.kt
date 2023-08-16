package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionDistrictView
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict

class GeoRegionDistrictViewListToGeoRegionDistrictsListMapper(mapper: GeoRegionDistrictViewToGeoRegionDistrictMapper) :
    ListMapperImpl<GeoRegionDistrictView, GeoRegionDistrict>(mapper)