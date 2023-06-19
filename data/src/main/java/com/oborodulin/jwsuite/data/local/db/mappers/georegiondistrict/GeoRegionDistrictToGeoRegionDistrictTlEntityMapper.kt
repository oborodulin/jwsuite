package com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict
import java.util.UUID

class GeoRegionDistrictToGeoRegionDistrictTlEntityMapper :
    Mapper<GeoRegionDistrict, GeoRegionDistrictTlEntity> {
    override fun map(input: GeoRegionDistrict) = GeoRegionDistrictTlEntity(
        regionDistrictTlId = input.tlId ?: input.apply { tlId = UUID.randomUUID() }.tlId!!,
        districtName = input.districtName,
        regionDistrictsId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!
    )
}