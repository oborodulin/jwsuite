package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.house.GetHousesForTerritoryUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HousesListToHousesListItemMapper

class HousesForTerritoryListConverter(private val mapper: HousesListToHousesListItemMapper) :
    CommonResultConverter<GetHousesForTerritoryUseCase.Response, List<HousesListItem>>() {
    override fun convertSuccess(data: GetHousesForTerritoryUseCase.Response) =
        mapper.map(data.houses)
}