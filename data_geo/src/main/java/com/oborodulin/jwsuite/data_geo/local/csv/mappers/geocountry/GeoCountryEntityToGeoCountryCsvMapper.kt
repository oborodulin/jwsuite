package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoCountryCsv

class GeoCountryEntityToGeoCountryCsvMapper : Mapper<GeoCountryEntity, GeoCountryCsv> {
    override fun map(input: GeoCountryEntity) = GeoCountryCsv(
        countryId = input.countryId,
        countryCode = input.countryCode,
        countryOsmId = input.countryOsmId,
        latitude = input.coordinates?.latitude,
        longitude = input.coordinates?.longitude
    )
}