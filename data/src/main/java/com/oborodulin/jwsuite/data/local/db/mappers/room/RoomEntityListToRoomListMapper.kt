package com.oborodulin.jwsuite.data.local.db.mappers.room

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.domain.model.Room

class RoomEntityListToRoomListMapper(mapper: RoomEntityToRoomMapper) :
    ListMapperImpl<RoomEntity, Room>(mapper)