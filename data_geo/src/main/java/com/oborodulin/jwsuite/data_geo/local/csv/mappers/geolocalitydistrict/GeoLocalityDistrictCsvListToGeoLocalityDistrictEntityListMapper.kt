package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv

class GeoLocalityDistrictCsvListToGeoLocalityDistrictEntityListMapper(mapper: GeoLocalityDistrictCsvToGeoLocalityDistrictEntityMapper) :
    ListMapperImpl<GeoLocalityDistrictCsv, GeoLocalityDistrictEntity>(mapper)