package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.house.GetHousesUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HousesListToHousesListItemMapper

class HousesListConverter(private val mapper: HousesListToHousesListItemMapper) :
    CommonResultConverter<GetHousesUseCase.Response, List<HousesListItem>>() {
    override fun convertSuccess(data: GetHousesUseCase.Response) = mapper.map(data.houses)
}