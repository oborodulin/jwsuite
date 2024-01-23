package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet.tl

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetTlCsv

class GeoStreetTlCsvListToGeoStreetTlEntityListMapper(mapper: GeoStreetTlCsvToGeoStreetTlEntityMapper) :
    ListMapperImpl<GeoStreetTlCsv, GeoStreetTlEntity>(mapper)