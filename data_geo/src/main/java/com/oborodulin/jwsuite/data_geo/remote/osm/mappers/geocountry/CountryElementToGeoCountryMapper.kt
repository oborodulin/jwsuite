package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geocountry

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.GeometryToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.model.country.CountryElement
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry

class CountryElementToGeoCountryMapper(private val mapper: GeometryToGeoCoordinatesMapper) :
    Mapper<CountryElement, GeoCountry> {
    override fun map(input: CountryElement) = GeoCountry(
        countryCode = input.tags.isoCode.ifEmpty { input.tags.countryCode },
        countryGeocode = input.tags.geocodeArea,
        countryOsmId = input.id,
        coordinates = mapper.map(input.geometry),
        countryName = input.tags.name
    )
}