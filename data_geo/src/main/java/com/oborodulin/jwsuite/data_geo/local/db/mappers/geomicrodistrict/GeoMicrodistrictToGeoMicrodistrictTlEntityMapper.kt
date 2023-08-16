package com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictTlEntity
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict
import java.util.UUID

class GeoMicrodistrictToGeoMicrodistrictTlEntityMapper :
    Mapper<GeoMicrodistrict, GeoMicrodistrictTlEntity> {
    override fun map(input: GeoMicrodistrict) = GeoMicrodistrictTlEntity(
        microdistrictTlId = input.tlId ?: input.apply { tlId = UUID.randomUUID() }.tlId!!,
        microdistrictName = input.microdistrictName,
        microdistrictsId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!
    )
}