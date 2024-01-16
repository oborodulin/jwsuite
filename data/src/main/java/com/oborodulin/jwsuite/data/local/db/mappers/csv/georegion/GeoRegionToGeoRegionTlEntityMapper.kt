package com.oborodulin.jwsuite.data.local.db.mappers.csv.georegion

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity
import java.util.UUID

class GeoRegionToGeoRegionTlEntityMapper : Mapper<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv, GeoRegionTlEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv) = GeoRegionTlEntity(
        regionTlId = input.tlId ?: input.apply { tlId = UUID.randomUUID() }.tlId!!,
        regionName = input.regionName,
        regionsId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!
    )
}