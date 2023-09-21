package com.oborodulin.jwsuite.domain.usecases.room

data class RoomUseCases(
    val getRoomsUseCase: GetRoomsUseCase,
    val getRoomUseCase: GetRoomUseCase,
    val getNextRoomNumUseCase: GetNextRoomNumUseCase,
    val getRoomsForTerritoryUseCase: GetRoomsForTerritoryUseCase,
    val saveRoomUseCase: SaveRoomUseCase,
    val saveTerritoryRoomUseCase: SaveTerritoryRoomUseCase,
    val deleteRoomUseCase: DeleteRoomUseCase,
    val deleteTerritoryRoomUseCase: DeleteTerritoryRoomUseCase
)