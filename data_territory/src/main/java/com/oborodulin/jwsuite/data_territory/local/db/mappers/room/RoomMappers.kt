package com.oborodulin.jwsuite.data_territory.local.db.mappers.room

data class RoomMappers(
    val roomViewListToRoomsListMapper: RoomViewListToRoomsListMapper,
    val roomViewToRoomMapper: RoomViewToRoomMapper,
    val roomEntityToRoomMapper: RoomEntityToRoomMapper,
    val roomsListToRoomEntityListMapper: RoomsListToRoomEntityListMapper,
    val roomToRoomEntityMapper: RoomToRoomEntityMapper
)
