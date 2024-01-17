package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionDistrictView

class GeoRegionDistrictViewListToGeoRegionDistrictsListMapper(mapper: GeoRegionDistrictViewToGeoRegionDistrictMapper) :
    ListMapperImpl<GeoRegionDistrictView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv>(mapper)