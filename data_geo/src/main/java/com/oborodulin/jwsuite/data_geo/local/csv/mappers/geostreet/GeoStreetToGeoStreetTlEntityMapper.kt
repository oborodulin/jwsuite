package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetTlEntity
import java.util.UUID

class GeoStreetToGeoStreetTlEntityMapper : Mapper<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv, GeoStreetTlEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv) = GeoStreetTlEntity(
        streetTlId = input.tlId ?: input.apply { tlId = UUID.randomUUID() }.tlId!!,
        streetName = input.streetName,
        streetsId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!
    )
}