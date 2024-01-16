package com.oborodulin.jwsuite.data.local.db.mappers.csv.geostreet

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import java.util.UUID

class GeoStreetToGeoStreetEntityMapper : Mapper<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv, GeoStreetEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv) = GeoStreetEntity(
        streetId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        streetHashCode = input.streetHashCode,
        roadType = input.roadType,
        isStreetPrivateSector = input.isPrivateSector,
        estStreetHouses = input.estimatedHouses,
        sLocalitiesId = input.locality.id!!
    )
}