package com.oborodulin.jwsuite.data.local.db.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict
import java.util.UUID

class GeoMicrodistrictToGeoMicrodistrictEntityMapper :
    Mapper<GeoMicrodistrict, GeoMicrodistrictEntity> {
    override fun map(input: GeoMicrodistrict) = GeoMicrodistrictEntity(
        microdistrictId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        microdistrictType = input.microdistrictType,
        microdistrictShortName = input.microdistrictShortName,
        localitiesId = input.localityId,
        localityDistrictsId = input.localityDistrictId
    )
}