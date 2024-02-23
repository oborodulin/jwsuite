package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoCountryCsv

class GeoCountryEntityListToGeoCountryCsvListMapper(mapper: GeoCountryEntityToGeoCountryCsvMapper) :
    ListMapperImpl<GeoCountryEntity, GeoCountryCsv>(mapper)