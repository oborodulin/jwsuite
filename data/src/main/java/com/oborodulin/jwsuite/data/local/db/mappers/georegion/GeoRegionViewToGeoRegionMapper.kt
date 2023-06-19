package com.oborodulin.jwsuite.data.local.db.mappers.georegion

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.views.GeoRegionView
import com.oborodulin.jwsuite.domain.model.GeoRegion

class GeoRegionViewToGeoRegionMapper : Mapper<GeoRegionView, GeoRegion> {
    override fun map(input: GeoRegionView): GeoRegion {
        val region = GeoRegion(
            regionCode = input.data.regionCode,
            regionName = input.tl.regionName
        )
        region.id = input.data.regionId
        region.tlId = input.tl.regionTlId
        return region
    }
}