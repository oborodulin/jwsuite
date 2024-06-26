package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geocountry

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.GeometryToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.model.CountryElement
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import com.oborodulin.jwsuite.domain.model.geo.GeoOsm

class CountryElementToGeoCountryMapper(private val mapper: GeometryToGeoCoordinatesMapper) :
    Mapper<CountryElement, GeoCountry> {
    override fun map(input: CountryElement) = GeoCountry(
        countryCode = input.tags.isoCode.ifBlank { input.tags.countryCode },
        osm = GeoOsm(
            geocode = input.tags.geocodeArea.ifBlank { input.tags.name },
            osmId = input.id,
            coordinates = mapper.map(input.geometry)
        ),
        countryName = input.tags.nameLoc.ifBlank { input.tags.name }
    )
}