package com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityView
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality

class LocalityViewListToGeoLocalitiesListMapper(mapper: LocalityViewToGeoLocalityMapper) :
    ListMapperImpl<LocalityView, GeoLocality>(mapper)