package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.Floor
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface FloorsRepository {
    fun getAll(): Flow<List<Floor>>
    fun getAllByHouse(houseId: UUID): Flow<List<Floor>>
    fun getAllByEntrance(entranceId: UUID): Flow<List<Floor>>
    fun getAllByTerritory(territoryId: UUID): Flow<List<Floor>>
    fun get(floorId: UUID): Flow<Floor>
    fun save(floor: Floor): Flow<Floor>
    fun delete(floor: Floor): Flow<Floor>
    fun deleteById(floorId: UUID): Flow<UUID>
    suspend fun deleteAll()
}