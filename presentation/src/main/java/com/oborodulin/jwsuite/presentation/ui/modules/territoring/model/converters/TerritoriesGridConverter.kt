package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territory.GetProcessAndLocationTerritoriesUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoriesListItem
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoriesListToTerritoriesListItemMapper

class TerritoriesGridConverter(private val mapper: TerritoriesListToTerritoriesListItemMapper) :
    CommonResultConverter<GetProcessAndLocationTerritoriesUseCase.Response, List<TerritoriesListItem>>() {
    override fun convertSuccess(data: GetProcessAndLocationTerritoriesUseCase.Response) =
        mapper.map(data.territories)
}