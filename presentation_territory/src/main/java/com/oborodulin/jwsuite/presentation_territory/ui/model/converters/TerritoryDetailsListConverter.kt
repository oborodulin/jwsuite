package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryDetailsUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryDetailsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryDetailsListToTerritoryDetailsListItemMapper

class TerritoryDetailsListConverter(private val mapper: TerritoryDetailsListToTerritoryDetailsListItemMapper) :
    CommonResultConverter<GetTerritoryDetailsUseCase.Response, List<TerritoryDetailsListItem>>() {
    override fun convertSuccess(data: GetTerritoryDetailsUseCase.Response) =
        mapper.map(data.territoryDetails)
}