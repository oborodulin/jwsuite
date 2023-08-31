package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territorycategory.GetTerritoryCategoriesUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryCategoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.category.TerritoryCategoriesListToTerritoryCategoriesListItemMapper

class TerritoryCategoriesListConverter(private val mapper: TerritoryCategoriesListToTerritoryCategoriesListItemMapper) :
    CommonResultConverter<GetTerritoryCategoriesUseCase.Response, List<TerritoryCategoriesListItem>>() {
    override fun convertSuccess(data: GetTerritoryCategoriesUseCase.Response) =
        mapper.map(data.territoryCategories)
}