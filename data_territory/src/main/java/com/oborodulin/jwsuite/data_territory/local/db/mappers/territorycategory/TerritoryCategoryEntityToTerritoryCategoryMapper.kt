package com.oborodulin.jwsuite.data_territory.local.db.mappers.territorycategory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.domain.model.territory.TerritoryCategory

class TerritoryCategoryEntityToTerritoryCategoryMapper :
    Mapper<TerritoryCategoryEntity, TerritoryCategory> {
    override fun map(input: TerritoryCategoryEntity): TerritoryCategory {
        val territoryCategory = TerritoryCategory(
            territoryCategoryCode = input.territoryCategoryCode,
            territoryCategoryMark = input.territoryCategoryMark,
            territoryCategoryName = input.territoryCategoryName
        )
        territoryCategory.id = input.territoryCategoryId
        return territoryCategory
    }
}