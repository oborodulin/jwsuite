package com.oborodulin.jwsuite.data.local.db.mappers.geostreet

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.domain.model.GeoStreet
import java.util.UUID

class GeoStreetToGeoStreetTlEntityMapper : Mapper<GeoStreet, GeoStreetTlEntity> {
    override fun map(input: GeoStreet) = GeoStreetTlEntity(
        streetTlId = input.tlId ?: input.apply { tlId = UUID.randomUUID() }.tlId!!,
        streetName = input.streetName,
        streetsId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!
    )
}