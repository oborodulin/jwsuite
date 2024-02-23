package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry.tl

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoCountryTlCsv

class GeoCountryTlEntityListToGeoCountryTlCsvListMapper(mapper: GeoCountryTlEntityToGeoCountryTlCsvMapper) :
    ListMapperImpl<GeoCountryTlEntity, GeoCountryTlCsv>(mapper)