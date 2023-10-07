package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.territory.Room
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomsListItem

class RoomsListToRoomsListItemMapper(mapper: RoomToRoomsListItemMapper) :
    ListMapperImpl<Room, RoomsListItem>(mapper)