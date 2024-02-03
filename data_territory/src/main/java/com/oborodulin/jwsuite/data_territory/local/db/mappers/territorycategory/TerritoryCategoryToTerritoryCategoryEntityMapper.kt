package com.oborodulin.jwsuite.data_territory.local.db.mappers.territorycategory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.domain.model.territory.TerritoryCategory
import java.util.UUID

class TerritoryCategoryToTerritoryCategoryEntityMapper :
    Mapper<TerritoryCategory, TerritoryCategoryEntity> {
    override fun map(input: TerritoryCategory) =
        TerritoryCategoryEntity(
            territoryCategoryId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
            territoryCategoryCode = input.territoryCategoryCode,
            territoryCategoryMark = input.territoryCategoryMark,
            territoryCategoryName = input.territoryCategoryName
        )
}