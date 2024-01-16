package com.oborodulin.jwsuite.data.local.db.mappers.csv.georegiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictTlEntity
import java.util.UUID

class GeoRegionDistrictToGeoRegionDistrictTlEntityMapper :
    Mapper<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv, GeoRegionDistrictTlEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv) = GeoRegionDistrictTlEntity(
        regionDistrictTlId = input.tlId ?: input.apply { tlId = UUID.randomUUID() }.tlId!!,
        regDistrictName = input.districtName,
        regionDistrictsId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!
    )
}