package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionView
import com.oborodulin.jwsuite.domain.model.GeoRegion

class GeoRegionViewToGeoRegionMapper : Mapper<GeoRegionView, GeoRegion>,
    NullableMapper<GeoRegionView, GeoRegion> {
    override fun map(input: GeoRegionView): GeoRegion {
        val region = GeoRegion(
            regionCode = input.data.regionCode,
            regionName = input.tl.regionName
        )
        region.id = input.data.regionId
        region.tlId = input.tl.regionTlId
        return region
    }

    override fun nullableMap(input: GeoRegionView?) = input?.let { map(it) }
}