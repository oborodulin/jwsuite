package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.domain.model.Territory
import java.util.UUID

class TerritoryToTerritoryEntityMapper : Mapper<Territory, TerritoryEntity> {
    override fun map(input: Territory) = TerritoryEntity(
        territoryId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        territoryNum = input.territoryNum,
        isActive = input.isActive,
        isBusiness = input.isBusiness,
        isGroupMinistry = input.isGroupMinistry,
        isInPerimeter = input.isInPerimeter,
        isProcessed = input.isProcessed,
        territoryDesc = input.territoryDesc,
        territoryCategoriesId = input.territoryCategory.id!!,
        congregationsId = input.congregation.id!!
    )
}