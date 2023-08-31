package com.oborodulin.jwsuite.data_territory.local.db.mappers.room

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.domain.model.Room

class RoomEntityListToRoomsListMapper(mapper: RoomEntityToRoomMapper) :
    ListMapperImpl<RoomEntity, Room>(mapper)