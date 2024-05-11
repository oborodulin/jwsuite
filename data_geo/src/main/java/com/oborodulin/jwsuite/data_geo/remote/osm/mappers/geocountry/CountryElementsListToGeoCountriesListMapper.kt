package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geocountry

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.remote.osm.model.CountryElement
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry

class CountryElementsListToGeoCountriesListMapper(mapper: CountryElementToGeoCountryMapper) :
    ListMapperImpl<CountryElement, GeoCountry>(mapper)