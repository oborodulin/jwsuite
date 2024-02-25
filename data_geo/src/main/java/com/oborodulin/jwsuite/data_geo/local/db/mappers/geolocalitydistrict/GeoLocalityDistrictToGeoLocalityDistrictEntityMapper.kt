package com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.mappers.GeoCoordinatesToCoordinatesMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict
import java.util.UUID

class GeoLocalityDistrictToGeoLocalityDistrictEntityMapper(private val mapper: GeoCoordinatesToCoordinatesMapper) :
    Mapper<GeoLocalityDistrict, GeoLocalityDistrictEntity> {
    override fun map(input: GeoLocalityDistrict) = GeoLocalityDistrictEntity(
        localityDistrictId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        locDistrictShortName = input.districtShortName,
        locDistrictGeocode = input.districtGeocode,
        locDistrictOsmId = input.districtOsmId,
        coordinates = mapper.map(input.coordinates),
        ldLocalitiesId = input.locality?.id!!
    )
}