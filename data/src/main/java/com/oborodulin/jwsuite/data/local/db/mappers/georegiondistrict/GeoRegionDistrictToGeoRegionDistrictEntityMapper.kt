package com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict
import java.util.UUID

class GeoRegionDistrictToGeoRegionDistrictEntityMapper :
    Mapper<GeoRegionDistrict, GeoRegionDistrictEntity> {
    override fun map(input: GeoRegionDistrict) = GeoRegionDistrictEntity(
        regionDistrictId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        districtShortName = input.districtShortName,
        rRegionsId = input.region.id!!
    )
}