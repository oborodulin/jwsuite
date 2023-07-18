package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoriesUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoriesListItem
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoriesListToTerritoriesListItemMapper

class TerritoriesListConverter(private val mapper: TerritoriesListToTerritoriesListItemMapper) :
    CommonResultConverter<GetTerritoriesUseCase.Response, List<TerritoriesListItem>>() {
    override fun convertSuccess(data: GetTerritoriesUseCase.Response) =
        mapper.map(data.territories)
}