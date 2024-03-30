package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.CoordinatesToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.RegionDistrictView
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict

class RegionDistrictViewToGeoRegionDistrictMapper(private val mapper: CoordinatesToGeoCoordinatesMapper) :
    Mapper<RegionDistrictView, GeoRegionDistrict>,
    NullableMapper<RegionDistrictView, GeoRegionDistrict> {
    override fun map(input: RegionDistrictView) = GeoRegionDistrict(
        districtShortName = input.data.regDistrictShortName, //GeoRegionDistrict.shortNameFromRegDistrictShortName(input.data.regDistrictShortName),
        districtType = input.data.regDistrictType,
        districtGeocode = input.data.regDistrictGeocode,
        districtOsmId = input.data.regDistrictOsmId,
        coordinates = mapper.map(input.data.coordinates),
        districtName = input.tl.regDistrictName
    ).also {
        it.id = input.data.regionDistrictId
        it.tlId = input.tl.regionDistrictTlId
    }

    override fun nullableMap(input: RegionDistrictView?) = input?.let { map(it) }
}