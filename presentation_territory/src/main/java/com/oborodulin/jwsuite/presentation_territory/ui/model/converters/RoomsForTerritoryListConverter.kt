package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.room.GetRoomsForTerritoryUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomsListToRoomsListItemMapper

class RoomsForTerritoryListConverter(private val mapper: RoomsListToRoomsListItemMapper) :
    CommonResultConverter<GetRoomsForTerritoryUseCase.Response, List<RoomsListItem>>() {
    override fun convertSuccess(data: GetRoomsForTerritoryUseCase.Response) =
        mapper.map(data.rooms)
}