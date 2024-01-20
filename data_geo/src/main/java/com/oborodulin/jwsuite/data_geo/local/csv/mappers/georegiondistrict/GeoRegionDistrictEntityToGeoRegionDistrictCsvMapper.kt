package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv

class GeoRegionDistrictEntityToGeoRegionDistrictCsvMapper :
    Mapper<GeoRegionDistrictEntity, GeoRegionDistrictCsv> {
    override fun map(input: GeoRegionDistrictEntity) = GeoRegionDistrictCsv(
        regionDistrictId = input.regionDistrictId,
        regDistrictShortName = input.regDistrictShortName,
        rRegionsId = input.rRegionsId
    )
}