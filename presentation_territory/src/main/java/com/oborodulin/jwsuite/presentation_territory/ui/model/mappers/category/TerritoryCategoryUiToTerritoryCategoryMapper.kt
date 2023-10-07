package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.category

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.territory.TerritoryCategory
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryCategoryUi

class TerritoryCategoryUiToTerritoryCategoryMapper :
    Mapper<TerritoryCategoryUi, TerritoryCategory> {
    override fun map(input: TerritoryCategoryUi): TerritoryCategory {
        val territoryCategory = TerritoryCategory(
            territoryCategoryCode = input.territoryCategoryCode,
            territoryCategoryMark = input.territoryCategoryMark,
            territoryCategoryName = input.territoryCategoryName
        )
        territoryCategory.id = input.id
        return territoryCategory
    }
}