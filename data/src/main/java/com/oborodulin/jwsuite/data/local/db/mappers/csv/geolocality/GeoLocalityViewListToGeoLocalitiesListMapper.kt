package com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocality

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityView

class GeoLocalityViewListToGeoLocalitiesListMapper(mapper: GeoLocalityViewToGeoLocalityMapper) :
    ListMapperImpl<GeoLocalityView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv>(mapper)