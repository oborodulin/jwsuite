package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.RegionDistrictView
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict

class RegionDistrictViewListToGeoRegionDistrictsListMapper(mapper: RegionDistrictViewToGeoRegionDistrictMapper) :
    ListMapperImpl<RegionDistrictView, GeoRegionDistrict>(mapper)