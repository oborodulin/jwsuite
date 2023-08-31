package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territorycategory.GetTerritoryCategoryUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryCategoryUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.category.TerritoryCategoryToTerritoryCategoryUiMapper

class TerritoryCategoryConverter(private val mapper: TerritoryCategoryToTerritoryCategoryUiMapper) :
    CommonResultConverter<GetTerritoryCategoryUseCase.Response, TerritoryCategoryUi>() {
    override fun convertSuccess(data: GetTerritoryCategoryUseCase.Response) =
        mapper.map(data.territoryCategory)
}