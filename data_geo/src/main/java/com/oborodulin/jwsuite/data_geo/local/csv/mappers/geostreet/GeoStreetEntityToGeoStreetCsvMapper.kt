package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv

class GeoStreetEntityToGeoStreetCsvMapper : Mapper<GeoStreetEntity, GeoStreetCsv> {
    override fun map(input: GeoStreetEntity) = GeoStreetCsv(
        streetId = input.streetId,
        streetHashCode = input.streetHashCode,
        roadType = input.roadType,
        isStreetPrivateSector = input.isStreetPrivateSector,
        estStreetHouses = input.estStreetHouses,
        streetOsmId = input.streetOsmId,
        latitude = input.coordinates.latitude,
        longitude = input.coordinates.longitude,
        sLocalitiesId = input.sLocalitiesId
    )
}