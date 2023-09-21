package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.room.GetRoomUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomToRoomUiMapper

class RoomConverter(private val mapper: RoomToRoomUiMapper) :
    CommonResultConverter<GetRoomUseCase.Response, RoomUi>() {
    override fun convertSuccess(data: GetRoomUseCase.Response) = mapper.map(data.room)
}