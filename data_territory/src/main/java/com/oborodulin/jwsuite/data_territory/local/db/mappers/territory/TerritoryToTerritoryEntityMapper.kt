package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.domain.model.territory.Territory
import java.util.UUID

class TerritoryToTerritoryEntityMapper : Mapper<Territory, TerritoryEntity> {
    override fun map(input: Territory) =
        TerritoryEntity(
            territoryId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
            territoryNum = input.territoryNum,
            isActive = input.isActive,
            isBusinessTerritory = input.isBusiness,
            isGroupMinistry = input.isGroupMinistry,
            //isInPerimeter = input.isInPerimeter,
            isProcessed = input.isProcessed,
            territoryDesc = input.territoryDesc,
            tMicrodistrictsId = input.microdistrict?.id,
            tLocalityDistrictsId = input.localityDistrict?.id,
            tLocalitiesId = input.locality.id!!,
            tTerritoryCategoriesId = input.territoryCategory.id!!,
            tCongregationsId = input.congregation.id!!
        )
}