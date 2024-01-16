package com.oborodulin.jwsuite.data.local.db.mappers.csv.georegion

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionView

class GeoRegionViewListToGeoRegionsListMapper(mapper: GeoRegionViewToGeoRegionMapper) :
    ListMapperImpl<GeoRegionView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv>(mapper)