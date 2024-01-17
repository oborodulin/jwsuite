package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion.tl

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionTlCsv

class GeoRegionTlCsvListToGeoRegionTlEntityListMapper(mapper: GeoRegionTlCsvToGeoRegionTlEntityMapper) :
    ListMapperImpl<GeoRegionTlCsv, GeoRegionTlEntity>(mapper)