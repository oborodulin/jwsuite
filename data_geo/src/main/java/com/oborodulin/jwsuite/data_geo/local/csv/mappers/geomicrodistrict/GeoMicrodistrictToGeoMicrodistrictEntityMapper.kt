package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import java.util.UUID

class GeoMicrodistrictToGeoMicrodistrictEntityMapper :
    Mapper<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv, GeoMicrodistrictEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv) = GeoMicrodistrictEntity(
        microdistrictId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        microdistrictType = input.microdistrictType,
        microdistrictShortName = input.microdistrictShortName,
        mLocalitiesId = input.locality.id!!,
        mLocalityDistrictsId = input.localityDistrict.id!!
    )
}