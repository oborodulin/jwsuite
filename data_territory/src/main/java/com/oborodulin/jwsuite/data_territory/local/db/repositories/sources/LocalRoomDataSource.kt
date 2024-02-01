package com.oborodulin.jwsuite.data_territory.local.db.repositories.sources

import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.RoomView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalRoomDataSource {
    fun getAllRooms(): Flow<List<RoomView>>
    fun getHouseRooms(houseId: UUID): Flow<List<RoomView>>
    fun getEntranceRooms(entranceId: UUID): Flow<List<RoomView>>
    fun getFloorRooms(floorId: UUID): Flow<List<RoomView>>
    fun getTerritoryRooms(territoryId: UUID): Flow<List<RoomView>>
    fun getRoomsForTerritory(territoryId: UUID): Flow<List<RoomView>>
    fun getRoom(roomId: UUID): Flow<RoomView>
    fun isHouseExistsRooms(houseId: UUID): Flow<Boolean>
    fun isEntranceExistsRooms(entranceId: UUID): Flow<Boolean>
    fun isFloorExistsRooms(floorId: UUID): Flow<Boolean>
    suspend fun insertRoom(room: RoomEntity)
    suspend fun updateRoom(room: RoomEntity)
    suspend fun deleteRoom(room: RoomEntity)
    suspend fun deleteRoomById(roomId: UUID)
    suspend fun deleteRooms(rooms: List<RoomEntity>)
    suspend fun deleteAllRooms()

    fun getNextRoomNum(houseId: UUID): Int
    suspend fun clearTerritoryById(roomId: UUID)
    suspend fun setTerritoryById(roomId: UUID, territoryId: UUID)

    // -------------------------------------- CSV Transfer --------------------------------------
    fun getRoomEntities(): Flow<List<RoomEntity>>
    suspend fun loadRoomEntities(rooms: List<RoomEntity>)
}