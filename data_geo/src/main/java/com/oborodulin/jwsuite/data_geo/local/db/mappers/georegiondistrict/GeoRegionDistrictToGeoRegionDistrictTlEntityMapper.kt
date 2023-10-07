package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict
import java.util.UUID

class GeoRegionDistrictToGeoRegionDistrictTlEntityMapper :
    Mapper<GeoRegionDistrict, GeoRegionDistrictTlEntity> {
    override fun map(input: GeoRegionDistrict) = GeoRegionDistrictTlEntity(
        regionDistrictTlId = input.tlId ?: input.apply { tlId = UUID.randomUUID() }.tlId!!,
        regDistrictName = input.districtName,
        regionDistrictsId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!
    )
}