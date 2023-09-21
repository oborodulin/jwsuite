package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.room.GetRoomsUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomsListToRoomsListItemMapper

class RoomsListConverter(private val mapper: RoomsListToRoomsListItemMapper) :
    CommonResultConverter<GetRoomsUseCase.Response, List<RoomsListItem>>() {
    override fun convertSuccess(data: GetRoomsUseCase.Response) = mapper.map(data.rooms)
}