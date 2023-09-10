package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.house.GetHouseUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.HouseUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseToHouseUiMapper

class HouseConverter(private val mapper: HouseToHouseUiMapper) :
    CommonResultConverter<GetHouseUseCase.Response, HouseUi>() {
    override fun convertSuccess(data: GetHouseUseCase.Response) = mapper.map(data.house)
}