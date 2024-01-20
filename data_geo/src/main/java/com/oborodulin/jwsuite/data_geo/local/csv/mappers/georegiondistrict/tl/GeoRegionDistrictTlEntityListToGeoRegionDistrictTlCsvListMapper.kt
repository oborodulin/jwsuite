package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.tl

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictTlCsv

class GeoRegionDistrictTlEntityListToGeoRegionDistrictTlCsvListMapper(mapper: GeoRegionDistrictTlEntityToGeoRegionDistrictTlCsvMapper) :
    ListMapperImpl<GeoRegionDistrictTlEntity, GeoRegionDistrictTlCsv>(mapper)