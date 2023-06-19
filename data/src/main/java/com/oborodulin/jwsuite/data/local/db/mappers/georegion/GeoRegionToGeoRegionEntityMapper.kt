package com.oborodulin.jwsuite.data.local.db.mappers.georegion

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.domain.model.GeoRegion
import java.util.UUID

class GeoRegionToGeoRegionEntityMapper : Mapper<GeoRegion, GeoRegionEntity> {
    override fun map(input: GeoRegion) = GeoRegionEntity(
        regionId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        regionCode = input.regionCode
    )
}