package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet.tl

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetTlCsv

class GeoStreetTlCsvToGeoStreetTlEntityMapper : Mapper<GeoStreetTlCsv, GeoStreetTlEntity> {
    override fun map(input: GeoStreetTlCsv) = GeoStreetTlEntity(
        streetTlId = input.streetTlId,
        streetLocCode = input.streetLocCode,
        streetName = input.streetName,
        streetsId = input.streetsId
    )
}