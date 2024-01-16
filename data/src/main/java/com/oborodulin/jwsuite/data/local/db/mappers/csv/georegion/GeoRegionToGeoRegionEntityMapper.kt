package com.oborodulin.jwsuite.data.local.db.mappers.csv.georegion

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import java.util.UUID

class GeoRegionToGeoRegionEntityMapper : Mapper<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv, GeoRegionEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv) = GeoRegionEntity(
        regionId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        regionCode = input.regionCode
    )
}