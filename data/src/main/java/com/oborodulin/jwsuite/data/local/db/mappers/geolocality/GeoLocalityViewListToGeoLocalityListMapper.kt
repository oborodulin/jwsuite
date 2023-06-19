package com.oborodulin.jwsuite.data.local.db.mappers.geolocality

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.views.GeoLocalityView
import com.oborodulin.jwsuite.domain.model.GeoLocality

class GeoLocalityViewListToGeoLocalityListMapper(mapper: GeoLocalityViewToGeoLocalityMapper) :
    ListMapperImpl<GeoLocalityView, GeoLocality>(mapper)