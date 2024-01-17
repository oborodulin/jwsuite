package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity

class GeoStreetsListToGeoStreetEntityListMapper(mapper: GeoStreetToGeoStreetEntityMapper) :
    ListMapperImpl<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv, GeoStreetEntity>(mapper)