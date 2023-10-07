package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict
import java.util.UUID

class GeoRegionDistrictToGeoRegionDistrictEntityMapper :
    Mapper<GeoRegionDistrict, GeoRegionDistrictEntity> {
    override fun map(input: GeoRegionDistrict) = GeoRegionDistrictEntity(
        regionDistrictId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        regDistrictShortName = input.districtShortName,
        rRegionsId = input.region.id!!
    )
}