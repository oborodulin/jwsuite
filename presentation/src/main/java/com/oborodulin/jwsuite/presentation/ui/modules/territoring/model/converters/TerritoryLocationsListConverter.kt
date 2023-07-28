package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryLocationsUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoringUi
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoryLocationsListToTerritoryLocationsListItemMapper

class TerritoryLocationsListConverter(private val mapper: TerritoryLocationsListToTerritoryLocationsListItemMapper) :
    CommonResultConverter<GetTerritoryLocationsUseCase.Response, TerritoringUi>() {
    override fun convertSuccess(data: GetTerritoryLocationsUseCase.Response) =
        TerritoringUi(territoryLocations = mapper.map(data.territoryLocations))
}