package com.oborodulin.jwsuite.presentation.ui.model.mappers.region

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoRegion
import com.oborodulin.jwsuite.presentation.ui.model.RegionListItem
import java.util.UUID

class RegionToRegionListItemMapper : Mapper<GeoRegion, RegionListItem> {
    override fun map(input: GeoRegion) = RegionListItem(
        id = input.id ?: UUID.randomUUID(),
        regionCode = input.regionCode,
        regionName = input.regionName
    )
}