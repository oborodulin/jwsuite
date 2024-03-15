package com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict
import java.util.UUID

class GeoLocalityDistrictToGeoLocalityDistrictTlEntityMapper :
    Mapper<GeoLocalityDistrict, GeoLocalityDistrictTlEntity> {
    override fun map(input: GeoLocalityDistrict) = GeoLocalityDistrictTlEntity(
        localityDistrictTlId = input.tlId ?: input.apply { tlId = UUID.randomUUID() }.tlId!!,
        locDistrictName = input.districtName,
        localityDistrictsId = input.id!! // ?: input.apply { id = UUID.randomUUID() }.id!!
    )
}