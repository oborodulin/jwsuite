package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv

class GeoStreetCsvListToGeoStreetEntityListMapper(mapper: GeoStreetCsvToGeoStreetEntityMapper) :
    ListMapperImpl<GeoStreetCsv, GeoStreetEntity>(mapper)