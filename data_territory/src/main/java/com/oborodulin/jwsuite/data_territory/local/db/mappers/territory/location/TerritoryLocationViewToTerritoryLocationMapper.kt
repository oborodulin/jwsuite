package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.location

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryLocationView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryLocation

class TerritoryLocationViewToTerritoryLocationMapper :
    Mapper<TerritoryLocationView, TerritoryLocation> {
    override fun map(input: TerritoryLocationView): TerritoryLocation {
        val territoryLocation = TerritoryLocation(
            territoryLocationType = input.territoryLocationType,
            congregationId = input.congregationId,
            isPrivateSector = input.isPrivateSector,
            locationId = input.locationId,
            locationShortName = input.locationShortName
        )
        territoryLocation.id = input.locationId
        return territoryLocation
    }
}