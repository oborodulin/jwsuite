package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.tl

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityTlCsv

class GeoLocalityTlCsvListToGeoLocalityTlEntityListMapper(mapper: GeoLocalityTlCsvToGeoLocalityTlEntityMapper) :
    ListMapperImpl<GeoLocalityTlCsv, GeoLocalityTlEntity>(mapper)