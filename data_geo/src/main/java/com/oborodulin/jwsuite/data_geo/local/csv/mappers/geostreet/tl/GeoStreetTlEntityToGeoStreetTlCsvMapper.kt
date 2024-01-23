package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet.tl

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetTlCsv

class GeoStreetTlEntityToGeoStreetTlCsvMapper : Mapper<GeoStreetTlEntity, GeoStreetTlCsv> {
    override fun map(input: GeoStreetTlEntity) = GeoStreetTlCsv(
        streetTlId = input.streetTlId,
        streetLocCode = input.streetLocCode,
        streetName = input.streetName,
        streetsId = input.streetsId
    )
}