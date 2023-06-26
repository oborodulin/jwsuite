package com.oborodulin.jwsuite.data.local.db.mappers.georegion

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.domain.model.GeoRegion

class GeoRegionsListToGeoRegionEntityListMapper(mapper: GeoRegionToGeoRegionEntityMapper) :
    ListMapperImpl<GeoRegion, GeoRegionEntity>(mapper)