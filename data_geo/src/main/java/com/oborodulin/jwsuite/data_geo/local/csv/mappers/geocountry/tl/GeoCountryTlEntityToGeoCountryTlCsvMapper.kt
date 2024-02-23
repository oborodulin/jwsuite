package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry.tl

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoCountryTlCsv

class GeoCountryTlEntityToGeoCountryTlCsvMapper : Mapper<GeoCountryTlEntity, GeoCountryTlCsv> {
    override fun map(input: GeoCountryTlEntity) = GeoCountryTlCsv(
        countryTlId = input.countryTlId,
        countryLocCode = input.countryLocCode,
        countryTlCode = input.countryTlCode,
        countryName = input.countryName,
        countriesId = input.countriesId
    )
}