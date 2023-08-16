package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity
import com.oborodulin.jwsuite.domain.model.GeoRegion
import java.util.UUID

class GeoRegionToGeoRegionTlEntityMapper : Mapper<GeoRegion, GeoRegionTlEntity> {
    override fun map(input: GeoRegion) = GeoRegionTlEntity(
        regionTlId = input.tlId ?: input.apply { tlId = UUID.randomUUID() }.tlId!!,
        regionName = input.regionName,
        regionsId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!
    )
}