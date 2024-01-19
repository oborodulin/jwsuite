package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.territory.Room
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RoomsRepository: CsvTransferableRepo {
    fun getAll(): Flow<List<Room>>
    fun getAllByHouse(houseId: UUID): Flow<List<Room>>
    fun getAllByEntrance(entranceId: UUID): Flow<List<Room>>
    fun getAllByFloor(floorId: UUID): Flow<List<Room>>
    fun getAllByTerritory(territoryId: UUID): Flow<List<Room>>
    fun getAllForTerritory(territoryId: UUID): Flow<List<Room>>
    fun get(roomId: UUID): Flow<Room>
    fun isExistsInHouse(houseId: UUID): Flow<Boolean>
    fun isExistsInEntrance(entranceId: UUID): Flow<Boolean>
    fun isExistsInFloor(floorId: UUID): Flow<Boolean>
    fun save(room: Room): Flow<Room>
    fun delete(room: Room): Flow<Room>
    fun deleteById(roomId: UUID): Flow<UUID>
    suspend fun deleteAll()

    fun getNextNum(houseId: UUID): Flow<Int>
    fun clearTerritory(roomId: UUID): Flow<UUID>
    fun setTerritory(roomIds: List<UUID> = emptyList(), territoryId: UUID): Flow<List<UUID>>
}