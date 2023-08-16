package com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.domain.model.GeoStreet
import java.util.UUID

class GeoStreetToGeoStreetEntityMapper : Mapper<GeoStreet, GeoStreetEntity> {
    override fun map(input: GeoStreet) = GeoStreetEntity(
        streetId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        streetHashCode = input.streetHashCode,
        roadType = input.roadType,
        isStreetPrivateSector = input.isPrivateSector,
        estStreetHouses = input.estimatedHouses,
        sLocalitiesId = input.locality.id!!
    )
}