package com.oborodulin.jwsuite.data.local.db.mappers.geolocality

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.domain.model.GeoLocality

class GeoLocalityListToGeoLocalityEntityListMapper(mapper: GeoLocalityToGeoLocalityEntityMapper) :
    ListMapperImpl<GeoLocality, GeoLocalityEntity>(mapper)