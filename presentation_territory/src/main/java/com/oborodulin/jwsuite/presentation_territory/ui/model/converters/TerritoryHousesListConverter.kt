package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.house.GetHousesForTerritoryUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryHousesUiModel
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryToTerritoryUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HousesListToHousesListItemMapper

class TerritoryHousesListConverter(
    private val territoryMapper: TerritoryToTerritoryUiMapper,
    private val housesListMapper: HousesListToHousesListItemMapper
) : CommonResultConverter<GetHousesForTerritoryUseCase.Response, TerritoryHousesUiModel>() {
    override fun convertSuccess(data: GetHousesForTerritoryUseCase.Response) =
        TerritoryHousesUiModel(
            territory = territoryMapper.map(data.territoryWithHouses.territory),
            houses = housesListMapper.map(data.territoryWithHouses.houses)
        )
}