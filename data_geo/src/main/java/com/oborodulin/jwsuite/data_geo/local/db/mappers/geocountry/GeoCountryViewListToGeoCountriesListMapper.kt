package com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoCountryView
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry

class GeoCountryViewListToGeoCountriesListMapper(mapper: GeoCountryViewToGeoCountryMapper) :
    ListMapperImpl<GeoCountryView, GeoCountry>(mapper)