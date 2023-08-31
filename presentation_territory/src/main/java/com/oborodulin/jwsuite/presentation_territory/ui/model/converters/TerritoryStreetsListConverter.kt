package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territory.street.GetTerritoryStreetsUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street.TerritoryStreetsListToTerritoryStreetsListItemMapper

class TerritoryStreetsListConverter(private val mapper: TerritoryStreetsListToTerritoryStreetsListItemMapper) :
    CommonResultConverter<GetTerritoryStreetsUseCase.Response, List<TerritoryStreetsListItem>>() {
    override fun convertSuccess(data: GetTerritoryStreetsUseCase.Response) =
        mapper.map(data.territoryStreets)
}