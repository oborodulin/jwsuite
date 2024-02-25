package com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.CoordinatesToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityDistrictView
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict

class LocalityDistrictViewToGeoLocalityDistrictMapper(private val mapper: CoordinatesToGeoCoordinatesMapper) :
    Mapper<LocalityDistrictView, GeoLocalityDistrict>,
    NullableMapper<LocalityDistrictView, GeoLocalityDistrict> {
    override fun map(input: LocalityDistrictView) = GeoLocalityDistrict(
        districtShortName = input.data.locDistrictShortName,
        districtGeocode = input.data.locDistrictGeocode,
        districtOsmId = input.data.locDistrictOsmId,
        coordinates = mapper.map(input.data.coordinates),
        districtName = input.tl.locDistrictName
    ).also {
        it.id = input.data.localityDistrictId
        it.tlId = input.tl.localityDistrictTlId
    }

    override fun nullableMap(input: LocalityDistrictView?) = input?.let { map(it) }
}