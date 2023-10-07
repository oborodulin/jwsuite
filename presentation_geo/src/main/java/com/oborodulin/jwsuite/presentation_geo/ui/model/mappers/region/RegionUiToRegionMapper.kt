package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionUi

class RegionUiToRegionMapper : Mapper<RegionUi, GeoRegion> {
    override fun map(input: RegionUi): GeoRegion {
        val region = GeoRegion(
            regionCode = input.regionCode,
            regionName = input.regionName
        )
        region.id = input.id
        return region
    }
}