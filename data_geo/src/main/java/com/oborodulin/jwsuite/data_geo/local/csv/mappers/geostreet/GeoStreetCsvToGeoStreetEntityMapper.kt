package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.Coordinates
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv

class GeoStreetCsvToGeoStreetEntityMapper : Mapper<GeoStreetCsv, GeoStreetEntity> {
    override fun map(input: GeoStreetCsv) = GeoStreetEntity(
        streetId = input.streetId,
        streetHashCode = input.streetHashCode,
        roadType = input.roadType,
        isStreetPrivateSector = input.isStreetPrivateSector,
        estStreetHouses = input.estStreetHouses,
        streetOsmId = input.streetOsmId,
        coordinates = Coordinates.fromLatAndLon(lat = input.latitude, lon = input.longitude),
        sLocalitiesId = input.sLocalitiesId
    )
}