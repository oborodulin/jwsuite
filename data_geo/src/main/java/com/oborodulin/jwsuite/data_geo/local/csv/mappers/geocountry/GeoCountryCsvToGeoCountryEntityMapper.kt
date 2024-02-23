package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.Coordinates
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoCountryCsv

class GeoCountryCsvToGeoCountryEntityMapper : Mapper<GeoCountryCsv, GeoCountryEntity> {
    override fun map(input: GeoCountryCsv) = GeoCountryEntity(
        countryId = input.countryId,
        countryCode = input.countryCode,
        countryGeocode = input.countryGeocode,
        countryOsmId = input.countryOsmId,
        coordinates = Coordinates.fromLatAndLon(lat = input.latitude, lon = input.longitude)
    )
}