package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.TerritoryLocation
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryLocationsListItem

class TerritoryLocationToTerritoryLocationsListItemMapper :
    Mapper<TerritoryLocation, TerritoryLocationsListItem> {
    override fun map(input: TerritoryLocation) = TerritoryLocationsListItem(
        locationId = input.id,
        locationShortName = input.locationShortName,
        territoryLocationType = input.territoryLocationType,
        congregationId = input.congregationId,
        isPrivateSector = input.isPrivateSector
    )
}