package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv

class GeoRegionDistrictCsvToGeoRegionDistrictEntityMapper :
    Mapper<GeoRegionDistrictCsv, GeoRegionDistrictEntity> {
    override fun map(input: GeoRegionDistrictCsv) = GeoRegionDistrictEntity(
        regionDistrictId = input.regionDistrictId,
        regDistrictShortName = input.regDistrictShortName,
        rRegionsId = input.rRegionsId
    )
}