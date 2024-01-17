package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import java.util.UUID

class GeoRegionDistrictToGeoRegionDistrictEntityMapper :
    Mapper<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv, GeoRegionDistrictEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv) = GeoRegionDistrictEntity(
        regionDistrictId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        regDistrictShortName = input.districtShortName,
        rRegionsId = input.region.id!!
    )
}