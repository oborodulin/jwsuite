package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv

class GeoMicrodistrictEntityToGeoMicrodistrictCsvMapper :
    Mapper<GeoMicrodistrictEntity, GeoMicrodistrictCsv> {
    override fun map(input: GeoMicrodistrictEntity) = GeoMicrodistrictCsv(
        microdistrictId = input.microdistrictId,
        microdistrictType = input.microdistrictType,
        microdistrictShortName = input.microdistrictShortName,
        microdistrictOsmId = input.microdistrictOsmId,
        latitude = input.coordinates?.latitude,
        longitude = input.coordinates?.longitude,
        mLocalityDistrictsId = input.mLocalityDistrictsId,
        mLocalitiesId = input.mLocalitiesId
    )
}