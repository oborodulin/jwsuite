package com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry

class GeoCountriesListToGeoCountryEntityListMapper(mapper: GeoCountryToGeoCountryEntityMapper) :
    ListMapperImpl<GeoCountry, GeoCountryEntity>(mapper)