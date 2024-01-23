package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv

class GeoLocalityCsvListToGeoLocalityEntityListMapper(mapper: GeoLocalityCsvToGeoLocalityEntityMapper) :
    ListMapperImpl<GeoLocalityCsv, GeoLocalityEntity>(mapper)