package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.RegionView
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion

class RegionViewListToGeoRegionsListMapper(mapper: RegionViewToGeoRegionMapper) :
    ListMapperImpl<RegionView, GeoRegion>(mapper)