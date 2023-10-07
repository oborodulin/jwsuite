package com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.domain.model.geo.GeoStreet

class GeoStreetViewListToGeoStreetsListMapper(mapper: GeoStreetViewToGeoStreetMapper) :
    ListMapperImpl<GeoStreetView, GeoStreet>(mapper)