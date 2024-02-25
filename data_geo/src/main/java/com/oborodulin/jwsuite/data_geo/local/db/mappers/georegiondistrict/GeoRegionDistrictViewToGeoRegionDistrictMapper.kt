package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry.GeoCountryViewToGeoCountryMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.RegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionDistrictView
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict

private const val TAG = "Data.GeoRegionDistrictViewToGeoRegionDistrictMapper"

class GeoRegionDistrictViewToGeoRegionDistrictMapper(
    private val countryMapper: GeoCountryViewToGeoCountryMapper,
    private val regionMapper: RegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper
) : Mapper<GeoRegionDistrictView, GeoRegionDistrict>,
    NullableMapper<GeoRegionDistrictView, GeoRegionDistrict> {
    override fun map(input: GeoRegionDistrictView) = regionDistrictMapper.map(input.district)
        .also { regionDistrict ->
            regionDistrict.region = regionMapper.map(input.region)
                .also { region -> region.country = countryMapper.map(input.country) }
        }

    override fun nullableMap(input: GeoRegionDistrictView?) = input?.let { map(it) }
}
