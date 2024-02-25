package com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.mappers.GeoCoordinatesToCoordinatesMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict
import java.util.UUID

class GeoMicrodistrictToGeoMicrodistrictEntityMapper(private val mapper: GeoCoordinatesToCoordinatesMapper) :
    Mapper<GeoMicrodistrict, GeoMicrodistrictEntity> {
    override fun map(input: GeoMicrodistrict) = GeoMicrodistrictEntity(
        microdistrictId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        microdistrictType = input.microdistrictType,
        microdistrictShortName = input.microdistrictShortName,
        microdistrictGeocode = input.microdistrictGeocode,
        microdistrictOsmId = input.microdistrictOsmId,
        coordinates = mapper.map(input.coordinates),
        mLocalitiesId = input.locality?.id!!,
        mLocalityDistrictsId = input.localityDistrict?.id!!
    )
}