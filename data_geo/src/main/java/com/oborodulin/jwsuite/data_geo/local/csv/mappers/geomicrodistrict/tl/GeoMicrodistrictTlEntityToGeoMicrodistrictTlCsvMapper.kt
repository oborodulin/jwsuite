package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict.tl

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictTlCsv

class GeoMicrodistrictTlEntityToGeoMicrodistrictTlCsvMapper :
    Mapper<GeoMicrodistrictTlEntity, GeoMicrodistrictTlCsv> {
    override fun map(input: GeoMicrodistrictTlEntity) = GeoMicrodistrictTlCsv(
        microdistrictTlId = input.microdistrictTlId,
        microdistrictLocCode = input.microdistrictLocCode,
        microdistrictName = input.microdistrictName,
        microdistrictsId = input.microdistrictsId
    )
}