package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionView
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion

class GeoRegionViewToGeoRegionMapper : Mapper<GeoRegionView, GeoRegion>,
    NullableMapper<GeoRegionView, GeoRegion> {
    override fun map(input: GeoRegionView) = GeoRegion(
        regionCode = input.data.regionCode,
        regionName = input.tl.regionName
    ).also {
        it.id = input.data.regionId
        it.tlId = input.tl.regionTlId
    }

    override fun nullableMap(input: GeoRegionView?) = input?.let { map(it) }
}