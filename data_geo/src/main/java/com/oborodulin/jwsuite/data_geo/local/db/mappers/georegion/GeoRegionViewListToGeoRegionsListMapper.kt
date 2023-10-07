package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionView
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion

class GeoRegionViewListToGeoRegionsListMapper(mapper: GeoRegionViewToGeoRegionMapper) :
    ListMapperImpl<GeoRegionView, GeoRegion>(mapper)