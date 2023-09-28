package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.entrance.GetEntrancesForTerritoryUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryEntrancesUiModel
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryToTerritoryUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance.EntrancesListToEntrancesListItemMapper

class TerritoryEntrancesListConverter(
    private val territoryMapper: TerritoryToTerritoryUiMapper,
    private val entrancesListMapper: EntrancesListToEntrancesListItemMapper
) : CommonResultConverter<GetEntrancesForTerritoryUseCase.Response, TerritoryEntrancesUiModel>() {
    override fun convertSuccess(data: GetEntrancesForTerritoryUseCase.Response) =
        TerritoryEntrancesUiModel(
            territory = territoryMapper.map(data.territoryWithEntrances.territory),
            entrances = entrancesListMapper.map(data.territoryWithEntrances.entrances)
        )
}