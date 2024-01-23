package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.tl

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityTlCsv

class GeoLocalityTlEntityToGeoLocalityTlCsvMapper : Mapper<GeoLocalityTlEntity, GeoLocalityTlCsv> {
    override fun map(input: GeoLocalityTlEntity) = GeoLocalityTlCsv(
        localityTlId = input.localityTlId,
        localityLocCode = input.localityLocCode,
        localityShortName = input.localityShortName,
        localityName = input.localityName,
        localitiesId = input.localitiesId
    )
}