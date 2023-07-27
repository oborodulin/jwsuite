package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.TerritoryLocation
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryLocationsListItem
import java.util.UUID

class TerritoryLocationToTerritoryLocationsListItemMapper :
    Mapper<TerritoryLocation, TerritoryLocationsListItem> {
    override fun map(input: TerritoryLocation) = TerritoryLocationsListItem(
        locationId = input.id ?: UUID.randomUUID(),
        locationName = input.locationName,
        territoryLocationType = input.territoryLocationType,
        congregationId = input.congregationId,
        isPrivateSector = input.isPrivateSector
    )
}