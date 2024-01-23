package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.tl

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityTlCsv

class GeoLocalityTlCsvToGeoLocalityTlEntityMapper : Mapper<GeoLocalityTlCsv, GeoLocalityTlEntity> {
    override fun map(input: GeoLocalityTlCsv) = GeoLocalityTlEntity(
        localityTlId = input.localityTlId,
        localityLocCode = input.localityLocCode,
        localityShortName = input.localityShortName,
        localityName = input.localityName,
        localitiesId = input.localitiesId
    )
}