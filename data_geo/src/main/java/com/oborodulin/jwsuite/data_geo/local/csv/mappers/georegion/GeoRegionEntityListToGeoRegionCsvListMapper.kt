package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionView

class GeoRegionEntityListToGeoRegionCsvListMapper(mapper: GeoRegionEntityToGeoRegionCsvMapper) :
    ListMapperImpl<GeoRegionView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv>(mapper)