package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.category

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.TerritoryCategory
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryCategoryUi

class TerritoryCategoryToTerritoryCategoryUiMapper :
    Mapper<TerritoryCategory, TerritoryCategoryUi> {
    override fun map(input: TerritoryCategory): TerritoryCategoryUi {
        val territoryCategoryUi = TerritoryCategoryUi(
            territoryCategoryCode = input.territoryCategoryCode,
            territoryCategoryMark = input.territoryCategoryMark,
            territoryCategoryName = input.territoryCategoryName
        )
        territoryCategoryUi.id = input.id
        return territoryCategoryUi
    }
}