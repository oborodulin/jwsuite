package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.room.GetRoomsForTerritoryUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryRoomsUiModel
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryToTerritoryUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomsListToRoomsListItemMapper

class TerritoryRoomsListConverter(
    private val territoryMapper: TerritoryToTerritoryUiMapper,
    private val roomsListMapper: RoomsListToRoomsListItemMapper
) : CommonResultConverter<GetRoomsForTerritoryUseCase.Response, TerritoryRoomsUiModel>() {
    override fun convertSuccess(data: GetRoomsForTerritoryUseCase.Response) =
        TerritoryRoomsUiModel(
            territory = territoryMapper.map(data.territoryWithRooms.territory),
            rooms = roomsListMapper.map(data.territoryWithRooms.rooms)
        )
}