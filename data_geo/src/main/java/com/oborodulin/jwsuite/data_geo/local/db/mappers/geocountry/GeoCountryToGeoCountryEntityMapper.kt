package com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.mappers.GeoCoordinatesToCoordinatesMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import java.util.UUID

class GeoCountryToGeoCountryEntityMapper(private val mapper: GeoCoordinatesToCoordinatesMapper) :
    Mapper<GeoCountry, GeoCountryEntity> {
    override fun map(input: GeoCountry) = GeoCountryEntity(
        countryId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        countryCode = input.countryCode,
        countryGeocode = input.countryGeocode,
        countryOsmId = input.countryOsmId,
        coordinates = mapper.map(input.coordinates)
    )
}