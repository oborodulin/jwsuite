package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territory.street.GetTerritoryStreetUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryStreetUi
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.street.TerritoryStreetToTerritoryStreetUiMapper

class TerritoryStreetConverter(private val mapper: TerritoryStreetToTerritoryStreetUiMapper) :
    CommonResultConverter<GetTerritoryStreetUseCase.Response, TerritoryStreetUi>() {
    override fun convertSuccess(data: GetTerritoryStreetUseCase.Response) =
        mapper.map(data.territoryStreet)
}