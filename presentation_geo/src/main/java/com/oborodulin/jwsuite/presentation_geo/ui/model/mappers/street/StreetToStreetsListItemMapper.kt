package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoStreet
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import java.util.UUID

class StreetToStreetsListItemMapper : Mapper<GeoStreet, StreetsListItem> {
    override fun map(input: GeoStreet) = StreetsListItem(
        id = input.id ?: UUID.randomUUID(),
        isPrivateSector = input.isPrivateSector,
        estimatedHouses = input.estimatedHouses,
        streetFullName = input.streetFullName,
        isPrivateSectorInfo = input.isPrivateSectorInfo,
        estHousesInfo = input.estHousesInfo
    )
}