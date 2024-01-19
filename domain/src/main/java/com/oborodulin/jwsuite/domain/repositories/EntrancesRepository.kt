package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.territory.Entrance
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface EntrancesRepository: CsvTransferableRepo {
    fun getAll(): Flow<List<Entrance>>
    fun getAllByHouse(houseId: UUID): Flow<List<Entrance>>
    fun getAllByTerritory(territoryId: UUID): Flow<List<Entrance>>
    fun getAllForTerritory(territoryId: UUID): Flow<List<Entrance>>
    fun get(entranceId: UUID): Flow<Entrance>
    fun isExistsInHouse(houseId: UUID): Flow<Boolean>
    fun save(entrance: Entrance): Flow<Entrance>
    fun delete(entrance: Entrance): Flow<Entrance>
    fun deleteById(entranceId: UUID): Flow<UUID>
    suspend fun deleteAll()

    fun getNextNum(houseId: UUID): Flow<Int>
    fun clearTerritory(entranceId: UUID): Flow<UUID>
    fun setTerritory(entranceIds: List<UUID> = emptyList(), territoryId: UUID): Flow<List<UUID>>
}