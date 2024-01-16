package com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocality

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity

class GeoLocalitiesListToGeoLocalityEntityListMapper(mapper: GeoLocalityToGeoLocalityEntityMapper) :
    ListMapperImpl<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv, GeoLocalityEntity>(mapper)