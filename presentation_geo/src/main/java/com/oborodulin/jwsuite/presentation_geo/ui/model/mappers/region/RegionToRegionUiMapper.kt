package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.GeoCoordinatesToCoordinatesUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country.CountryToCountryUiMapper

class RegionToRegionUiMapper(
    private val countryMapper: CountryToCountryUiMapper,
    private val coordinatesMapper: GeoCoordinatesToCoordinatesUiMapper
) : Mapper<GeoRegion, RegionUi>, NullableMapper<GeoRegion, RegionUi> {
    override fun map(input: GeoRegion) = RegionUi(
        country = countryMapper.nullableMap(input.country),
        codePrefix = input.prefix,
        regionCode = input.code,
        regionType = input.regionType,
        isRegionTypePrefix = input.isRegionTypePrefix,
        regionGeocode = input.regionGeocode,
        regionOsmId = input.regionOsmId,
        coordinates = coordinatesMapper.map(input.coordinates),
        regionName = input.regionName,
        regionFullName = input.regionFullName
    ).also {
        it.id = input.id
        it.tlId = input.tlId
    }

    override fun nullableMap(input: GeoRegion?) = input?.let { map(it) }
}