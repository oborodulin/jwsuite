package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityDistrictView

class GeoLocalityDistrictViewListToGeoLocalityDistrictsListMapper(mapper: GeoLocalityDistrictViewToGeoLocalityDistrictMapper) :
    ListMapperImpl<GeoLocalityDistrictView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv>(mapper)