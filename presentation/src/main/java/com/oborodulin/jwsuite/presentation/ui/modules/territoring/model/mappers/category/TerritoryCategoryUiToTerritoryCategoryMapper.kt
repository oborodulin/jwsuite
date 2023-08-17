package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.category

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.TerritoryCategory
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryCategoryUi

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