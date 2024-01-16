package com.oborodulin.jwsuite.data.local.db.mappers.csv.georegion

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity

class GeoRegionsListToGeoRegionEntityListMapper(mapper: GeoRegionToGeoRegionEntityMapper) :
    ListMapperImpl<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv, GeoRegionEntity>(mapper)