package com.oborodulin.jwsuite.data.local.db.mappers.geostreet

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.domain.model.GeoStreet

class GeoStreetViewListToGeoStreetListMapper(mapper: GeoStreetViewToGeoStreetMapper) :
    ListMapperImpl<GeoStreetView, GeoStreet>(mapper)