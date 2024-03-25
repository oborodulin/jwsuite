package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.CoordinatesToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.RegionView
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion

class RegionViewToGeoRegionMapper(
    private val ctx: Context,
    private val mapper: CoordinatesToGeoCoordinatesMapper
) : Mapper<RegionView, GeoRegion>, NullableMapper<RegionView, GeoRegion> {
    override fun map(input: RegionView) = GeoRegion(
        ctx = ctx,
        regionCode = input.data.regionCode.substringAfterLast('-'),
        regionType = input.data.regionType,
        regionGeocode = input.data.regionGeocode,
        regionOsmId = input.data.regionOsmId,
        coordinates = mapper.map(input.data.coordinates),
        regionName = input.tl.regionName
    ).also {
        it.id = input.data.regionId
        it.tlId = input.tl.regionTlId
    }

    override fun nullableMap(input: RegionView?) = input?.let { map(it) }
}