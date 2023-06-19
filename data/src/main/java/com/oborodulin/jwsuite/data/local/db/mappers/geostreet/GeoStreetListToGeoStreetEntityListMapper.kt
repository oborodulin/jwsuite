package com.oborodulin.jwsuite.data.local.db.mappers.geostreet

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.domain.model.GeoStreet

class GeoStreetListToGeoStreetEntityListMapper(mapper: GeoStreetToGeoStreetEntityMapper) :
    ListMapperImpl<GeoStreet, GeoStreetEntity>(mapper)