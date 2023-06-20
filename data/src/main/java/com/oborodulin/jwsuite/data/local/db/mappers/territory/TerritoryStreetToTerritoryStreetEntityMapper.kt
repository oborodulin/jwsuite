package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.domain.model.TerritoryStreet
import java.util.UUID

class TerritoryStreetToTerritoryStreetEntityMapper :
    Mapper<TerritoryStreet, TerritoryStreetEntity> {
    override fun map(input: TerritoryStreet) = TerritoryStreetEntity(
        territoryStreetId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        isEven = input.isEven,
        isPrivateSector = input.isPrivateSector,
        estimatedHouses = input.estimatedHouses,
        streetsId = input.street.id!!,
        territoriesId = input.territoryId
    )
}