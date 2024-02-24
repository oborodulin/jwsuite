package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.GeoCoordinatesToCoordinatesUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country.CountryToCountryUiMapper

class RegionToRegionUiMapper(
    private val countryMapper: CountryToCountryUiMapper,
    private val coordinatesMapper: GeoCoordinatesToCoordinatesUiMapper
) : Mapper<GeoRegion, RegionUi> {
    override fun map(input: GeoRegion) = RegionUi(
        country = countryMapper.map(input.country!!),
        regionCode = input.regionCode,
        regionGeocode = input.regionGeocode,
        regionOsmId = input.regionOsmId,
        coordinates = coordinatesMapper.map(input.coordinates),
        regionName = input.regionName
    ).also { it.id = input.id }
}