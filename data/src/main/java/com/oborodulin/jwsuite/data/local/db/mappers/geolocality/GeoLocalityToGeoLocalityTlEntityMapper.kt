package com.oborodulin.jwsuite.data.local.db.mappers.geolocality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityTlEntity
import com.oborodulin.jwsuite.domain.model.GeoLocality
import java.util.UUID

class GeoLocalityToGeoLocalityTlEntityMapper : Mapper<GeoLocality, GeoLocalityTlEntity> {
    override fun map(input: GeoLocality) = GeoLocalityTlEntity(
        localityTlId = input.tlId ?: input.apply { tlId = UUID.randomUUID() }.tlId!!,
        localityName = input.localityName,
        localitiesId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!
    )
}