package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryToTerritoryUiMapper

class TerritoryConverter(private val mapper: TerritoryToTerritoryUiMapper) :
    CommonResultConverter<GetTerritoryUseCase.Response, TerritoryUi>() {
    override fun convertSuccess(data: GetTerritoryUseCase.Response) =
        mapper.map(data.territory)
}