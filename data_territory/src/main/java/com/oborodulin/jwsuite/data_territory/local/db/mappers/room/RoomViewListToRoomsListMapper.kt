package com.oborodulin.jwsuite.data_territory.local.db.mappers.room

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.views.RoomView
import com.oborodulin.jwsuite.domain.model.territory.Room

class RoomViewListToRoomsListMapper(mapper: RoomViewToRoomMapper) :
    ListMapperImpl<RoomView, Room>(mapper)