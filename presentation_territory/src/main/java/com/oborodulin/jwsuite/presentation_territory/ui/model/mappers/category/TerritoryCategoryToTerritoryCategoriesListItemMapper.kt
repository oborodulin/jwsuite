package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.category

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.TerritoryCategory
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryCategoriesListItem
import java.util.UUID

class TerritoryCategoryToTerritoryCategoriesListItemMapper :
    Mapper<TerritoryCategory, TerritoryCategoriesListItem> {
    override fun map(input: TerritoryCategory) = TerritoryCategoriesListItem(
        id = input.id ?: UUID.randomUUID(),
        territoryCategoryCode = input.territoryCategoryCode,
        territoryCategoryMark = input.territoryCategoryMark,
        territoryCategoryName = input.territoryCategoryName
    )
}