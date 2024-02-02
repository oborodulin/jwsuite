package com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryCsv

class TerritoryEntityToTerritoryCsvMapper : Mapper<TerritoryEntity, TerritoryCsv> {
    override fun map(input: TerritoryEntity) = TerritoryCsv(
        territoryId = input.territoryId,
        territoryNum = input.territoryNum,
        isActive = input.isActive,
        isBusinessTerritory = input.isBusinessTerritory,
        isGroupMinistry = input.isGroupMinistry,
        isProcessed = input.isProcessed,
        territoryDesc = input.territoryDesc,
        tMicrodistrictsId = input.tMicrodistrictsId,
        tLocalityDistrictsId = input.tLocalityDistrictsId,
        tLocalitiesId = input.tLocalitiesId,
        tTerritoryCategoriesId = input.tTerritoryCategoriesId,
        tCongregationsId = input.tCongregationsId
    )
}