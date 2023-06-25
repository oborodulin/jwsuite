package com.oborodulin.jwsuite.presentation.ui.model.mappers.region

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoRegion
import com.oborodulin.jwsuite.presentation.ui.model.RegionUi

class RegionToRegionUiMapper : Mapper<GeoRegion, RegionUi> {
    override fun map(input: GeoRegion): RegionUi {
        val regionUi = RegionUi(
            regionCode = input.regionCode,
            regionName = input.regionName
        )
        regionUi.id = input.id
        return regionUi
    }
}