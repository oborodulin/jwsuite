package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.Entrance
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface EntrancesRepository {
    fun getAll(): Flow<List<Entrance>>
    fun getAllByHouse(houseId: UUID): Flow<List<Entrance>>
    fun getAllByTerritory(territoryId: UUID): Flow<List<Entrance>>
    fun get(entranceId: UUID): Flow<Entrance>
    fun save(entrance: Entrance): Flow<Entrance>
    fun delete(entrance: Entrance): Flow<Entrance>
    fun deleteById(entranceId: UUID): Flow<UUID>
    suspend fun deleteAll()
}