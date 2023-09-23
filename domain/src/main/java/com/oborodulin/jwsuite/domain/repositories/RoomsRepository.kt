package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.Room
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RoomsRepository {
    fun getAll(): Flow<List<Room>>
    fun getAllByHouse(houseId: UUID): Flow<List<Room>>
    fun getAllByEntrance(entranceId: UUID): Flow<List<Room>>
    fun getAllByFloor(floorId: UUID): Flow<List<Room>>
    fun getAllByTerritory(territoryId: UUID): Flow<List<Room>>
    fun getAllForTerritory(territoryId: UUID): Flow<List<Room>>
    fun get(roomId: UUID): Flow<Room>
    fun save(room: Room): Flow<Room>
    fun delete(room: Room): Flow<Room>
    fun deleteById(roomId: UUID): Flow<UUID>
    suspend fun deleteAll()

    fun getNextRoomNum(houseId: UUID): Flow<Int>
    fun clearTerritory(roomId: UUID): Flow<UUID>
    fun setTerritory(roomIds: List<UUID> = emptyList(), territoryId: UUID): Flow<List<UUID>>
}