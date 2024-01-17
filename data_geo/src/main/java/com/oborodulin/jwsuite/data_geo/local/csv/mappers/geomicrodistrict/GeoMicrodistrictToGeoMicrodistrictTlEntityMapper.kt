package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictTlEntity
import java.util.UUID

class GeoMicrodistrictToGeoMicrodistrictTlEntityMapper :
    Mapper<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv, GeoMicrodistrictTlEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv) = GeoMicrodistrictTlEntity(
        microdistrictTlId = input.tlId ?: input.apply { tlId = UUID.randomUUID() }.tlId!!,
        microdistrictName = input.microdistrictName,
        microdistrictsId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!
    )
}