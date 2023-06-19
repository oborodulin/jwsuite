package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.domain.model.TerritoryCategory

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