package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict.tl

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictTlCsv

class GeoLocalityDistrictTlCsvListToGeoLocalityTlEntityListMapper(mapper: GeoLocalityDistrictTlCsvToGeoLocalityDistrictTlEntityMapper) :
    ListMapperImpl<GeoLocalityDistrictTlCsv, GeoLocalityDistrictTlEntity>(mapper)