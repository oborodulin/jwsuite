package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv

class GeoMicrodistrictCsvToGeoMicrodistrictEntityMapper :
    Mapper<GeoMicrodistrictCsv, GeoMicrodistrictEntity> {
    override fun map(input: GeoMicrodistrictCsv) = GeoMicrodistrictEntity(
        microdistrictId = input.microdistrictId,
        microdistrictType = input.microdistrictType,
        microdistrictShortName = input.microdistrictShortName,
        mLocalityDistrictsId = input.mLocalityDistrictsId,
        mLocalitiesId = input.mLocalitiesId
    )
}