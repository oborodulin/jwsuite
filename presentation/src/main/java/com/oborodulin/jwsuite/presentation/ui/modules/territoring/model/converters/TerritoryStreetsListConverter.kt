package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryStreetsUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryStreetsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoryStreetsListToTerritoryStreetsListItemMapper

class TerritoryStreetsListConverter(private val mapper: TerritoryStreetsListToTerritoryStreetsListItemMapper) :
    CommonResultConverter<GetTerritoryStreetsUseCase.Response, List<TerritoryStreetsListItem>>() {
    override fun convertSuccess(data: GetTerritoryStreetsUseCase.Response) =
        mapper.map(data.territoryStreets)
}