package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.tl

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictTlCsv

class GeoRegionDistrictTlCsvListToGeoRegionDistrictTlEntityListMapper(mapper: GeoRegionDistrictTlCsvToGeoRegionDistrictTlEntityMapper) :
    ListMapperImpl<GeoRegionDistrictTlCsv, GeoRegionDistrictTlEntity>(mapper)