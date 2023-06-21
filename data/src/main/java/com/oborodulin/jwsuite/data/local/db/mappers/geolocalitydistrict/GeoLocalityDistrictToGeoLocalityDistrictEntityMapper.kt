package com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict
import java.util.UUID

class GeoLocalityDistrictToGeoLocalityDistrictEntityMapper :
    Mapper<GeoLocalityDistrict, GeoLocalityDistrictEntity> {
    override fun map(input: GeoLocalityDistrict) = GeoLocalityDistrictEntity(
        localityDistrictId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        districtShortName = input.districtShortName,
        ldLocalitiesId = input.localityId
    )
}