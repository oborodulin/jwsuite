package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.CoordinatesToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry.GeoCountryViewToGeoCountryMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionView
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion

class GeoRegionViewToGeoRegionMapper(
    private val countryMapper: GeoCountryViewToGeoCountryMapper,
    private val coordinatesMapper: CoordinatesToGeoCoordinatesMapper
) : Mapper<GeoRegionView, GeoRegion>, NullableMapper<GeoRegionView, GeoRegion> {
    override fun map(input: GeoRegionView) = GeoRegion(
        country = countryMapper.map(input.country),
        regionCode = input.region.data.regionCode,
        regionGeocode = input.region.data.regionGeocode,
        regionOsmId = input.region.data.regionOsmId,
        coordinates = coordinatesMapper.map(input.region.data.coordinates),
        regionName = input.region.tl.regionName
    ).also {
        it.id = input.region.data.regionId
        it.tlId = input.region.tl.regionTlId
    }

    override fun nullableMap(input: GeoRegionView?) = input?.let { map(it) }
}