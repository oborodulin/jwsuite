package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territory.street.GetTerritoryStreetUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUiModel
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street.TerritoryStreetWithTerritoryAndStreetsToTerritoryStreetUiModelMapper

class TerritoryStreetConverter(private val mapper: TerritoryStreetWithTerritoryAndStreetsToTerritoryStreetUiModelMapper) :
    CommonResultConverter<GetTerritoryStreetUseCase.Response, TerritoryStreetUiModel>() {
    override fun convertSuccess(data: GetTerritoryStreetUseCase.Response) =
        mapper.map(data.territoryStreetWithTerritoryAndStreets)
}