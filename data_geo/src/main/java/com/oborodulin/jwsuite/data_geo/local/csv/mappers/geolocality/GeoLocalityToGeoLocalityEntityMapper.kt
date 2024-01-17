package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import java.util.UUID

class GeoLocalityToGeoLocalityEntityMapper : Mapper<com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv, GeoLocalityEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv) = GeoLocalityEntity(
        localityId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        localityCode = input.localityCode,
        localityType = input.localityType,
        lRegionDistrictsId = input.regionDistrict?.id,
        lRegionsId = input.region.id!!
    )
}