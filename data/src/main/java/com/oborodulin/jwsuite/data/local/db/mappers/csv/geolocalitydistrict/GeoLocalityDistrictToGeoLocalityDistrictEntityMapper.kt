package com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocalitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import java.util.UUID

class GeoLocalityDistrictToGeoLocalityDistrictEntityMapper :
    Mapper<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv, GeoLocalityDistrictEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv) = GeoLocalityDistrictEntity(
        localityDistrictId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        locDistrictShortName = input.districtShortName,
        ldLocalitiesId = input.locality.id!!
    )
}