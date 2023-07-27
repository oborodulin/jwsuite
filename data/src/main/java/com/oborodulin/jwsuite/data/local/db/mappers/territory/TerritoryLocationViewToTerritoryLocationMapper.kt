package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.views.TerritoryLocationView
import com.oborodulin.jwsuite.domain.model.TerritoryLocation
import java.util.UUID

class TerritoryLocationViewToTerritoryLocationMapper :
    Mapper<TerritoryLocationView, TerritoryLocation> {
    override fun map(input: TerritoryLocationView): TerritoryLocation {
        val territoryLocation = TerritoryLocation(
            territoryLocationType = input.territoryLocationType,
            congregationId = input.congregationId,
            isPrivateSector = input.isPrivateSector,
            locationId = input.locationId,
            locationName = input.locationName
        )
        territoryLocation.id = UUID.randomUUID()
        return territoryLocation
    }
}