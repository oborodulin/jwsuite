package com.oborodulin.jwsuite.presentation_geo.model.mappers.region

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoRegion
import com.oborodulin.jwsuite.presentation_geo.model.RegionsListItem
import java.util.UUID

class RegionToRegionsListItemMapper : Mapper<GeoRegion, RegionsListItem> {
    override fun map(input: GeoRegion) = RegionsListItem(
        id = input.id ?: UUID.randomUUID(),
        regionCode = input.regionCode,
        regionName = input.regionName
    )
}