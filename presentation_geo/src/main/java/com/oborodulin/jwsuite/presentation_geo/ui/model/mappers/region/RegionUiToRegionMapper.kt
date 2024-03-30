package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region

import com.oborodulin.home.common.extensions.ifNotEmpty
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.CoordinatesUiToGeoCoordinatesMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country.CountryUiToCountryMapper

class RegionUiToRegionMapper(
    private val countryUiMapper: CountryUiToCountryMapper,
    private val coordinatesUiMapper: CoordinatesUiToGeoCoordinatesMapper
) : Mapper<RegionUi, GeoRegion>, NullableMapper<RegionUi, GeoRegion> {
    override fun map(input: RegionUi) = GeoRegion(
        country = countryUiMapper.map(input.country!!),
        regionCode = input.regionPrefix.ifNotEmpty { "$it-${input.regionCode}" }
            ?: input.regionCode,
        regionType = input.regionType,
        isRegionTypePrefix = input.isRegionTypePrefix,
        regionGeocode = input.regionGeocode,
        regionOsmId = input.regionOsmId,
        coordinates = coordinatesUiMapper.map(input.coordinates),
        regionName = input.regionName
    ).also {
        it.id = input.id
        it.tlId = input.tlId
    }

    override fun nullableMap(input: RegionUi?) = input?.let { map(it) }
}