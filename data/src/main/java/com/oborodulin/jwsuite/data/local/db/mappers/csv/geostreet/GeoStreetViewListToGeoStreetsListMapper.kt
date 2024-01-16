package com.oborodulin.jwsuite.data.local.db.mappers.csv.geostreet

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoStreetView

class GeoStreetViewListToGeoStreetsListMapper(mapper: GeoStreetViewToGeoStreetMapper) :
    ListMapperImpl<GeoStreetView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv>(mapper)