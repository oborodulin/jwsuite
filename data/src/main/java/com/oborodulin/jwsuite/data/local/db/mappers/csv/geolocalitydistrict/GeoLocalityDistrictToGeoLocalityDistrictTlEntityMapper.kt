package com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocalitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictTlEntity
import java.util.UUID

class GeoLocalityDistrictToGeoLocalityDistrictTlEntityMapper :
    Mapper<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv, GeoLocalityDistrictTlEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv) = GeoLocalityDistrictTlEntity(
        localityDistrictTlId = input.tlId ?: input.apply { tlId = UUID.randomUUID() }.tlId!!,
        locDistrictName = input.districtName,
        localityDistrictsId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!
    )
}