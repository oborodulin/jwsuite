package com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryTlEntity
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import java.util.UUID

class GeoCountryToGeoCountryTlEntityMapper : Mapper<GeoCountry, GeoCountryTlEntity> {
    override fun map(input: GeoCountry) = GeoCountryTlEntity(
        countryTlId = input.tlId ?: input.apply { tlId = UUID.randomUUID() }.tlId!!,
        countryName = input.countryName,
        countriesId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!
    )
}