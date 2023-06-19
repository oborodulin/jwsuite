package com.oborodulin.jwsuite.data.local.db.mappers.room

data class RoomMappers(
    val roomEntityListToRoomListMapper: RoomEntityListToRoomListMapper,
    val roomEntityToRoomMapper: RoomEntityToRoomMapper,
    val roomListToRoomEntityListMapper: RoomListToRoomEntityListMapper,
    val roomToRoomEntityMapper: RoomToRoomEntityMapper
)
