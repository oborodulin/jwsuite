package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv

class GeoRegionCsvListToGeoRegionEntityListMapper(mapper: GeoRegionCsvToGeoRegionEntityMapper) :
    ListMapperImpl<GeoRegionCsv, GeoRegionEntity>(mapper)