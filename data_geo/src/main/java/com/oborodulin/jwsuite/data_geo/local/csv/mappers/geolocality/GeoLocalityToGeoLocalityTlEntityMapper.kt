package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityTlEntity
import java.util.UUID

class GeoLocalityToGeoLocalityTlEntityMapper : Mapper<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv, GeoLocalityTlEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv) = GeoLocalityTlEntity(
        localityTlId = input.tlId ?: input.apply { tlId = UUID.randomUUID() }.tlId!!,
        localityShortName = input.localityShortName,
        localityName = input.localityName,
        localitiesId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!
    )
}