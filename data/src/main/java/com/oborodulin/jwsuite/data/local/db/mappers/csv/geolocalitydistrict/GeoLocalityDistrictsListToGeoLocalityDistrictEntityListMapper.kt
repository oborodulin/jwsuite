package com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocalitydistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity

class GeoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper(mapper: GeoLocalityDistrictToGeoLocalityDistrictEntityMapper) :
    ListMapperImpl<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv, GeoLocalityDistrictEntity>(mapper)