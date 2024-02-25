package com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.mappers.GeoCoordinatesToCoordinatesMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import java.util.UUID

class GeoLocalityToGeoLocalityEntityMapper(private val mapper: GeoCoordinatesToCoordinatesMapper) :
    Mapper<GeoLocality, GeoLocalityEntity> {
    override fun map(input: GeoLocality) = GeoLocalityEntity(
        localityId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        localityCode = input.localityCode,
        localityType = input.localityType,
        localityGeocode = input.localityGeocode,
        localityOsmId = input.localityOsmId,
        coordinates = mapper.map(input.coordinates),
        lRegionDistrictsId = input.regionDistrict?.id,
        lRegionsId = input.region?.id!!
    )
}