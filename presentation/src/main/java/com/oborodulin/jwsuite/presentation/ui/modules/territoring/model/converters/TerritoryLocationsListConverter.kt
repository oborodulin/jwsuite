package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryLocationsUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryLocationsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoryLocationsListToTerritoryLocationsListItemMapper

class TerritoryLocationsListConverter(private val mapper: TerritoryLocationsListToTerritoryLocationsListItemMapper) :
    CommonResultConverter<GetTerritoryLocationsUseCase.Response, List<TerritoryLocationsListItem>>() {
    override fun convertSuccess(data: GetTerritoryLocationsUseCase.Response) =
        mapper.map(data.territoryLocations)
}