package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data.local.db.entities.RoomEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalRoomDataSource {
    fun getAllRooms(): Flow<List<RoomEntity>>
    fun getHouseRooms(houseId: UUID): Flow<List<RoomEntity>>
    fun getEntranceRooms(entranceId: UUID): Flow<List<RoomEntity>>
    fun getFloorRooms(floorId: UUID): Flow<List<RoomEntity>>
    fun getTerritoryRooms(territoryId: UUID): Flow<List<RoomEntity>>
    fun getRoom(roomId: UUID): Flow<RoomEntity>
    suspend fun insertRoom(room: RoomEntity)
    suspend fun updateRoom(room: RoomEntity)
    suspend fun deleteRoom(room: RoomEntity)
    suspend fun deleteRoomById(roomId: UUID)
    suspend fun deleteRooms(rooms: List<RoomEntity>)
    suspend fun deleteAllRooms()
}