package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.House
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface HousesRepository {
    fun getAll(): Flow<List<House>>
    fun getAllByStreet(streetId: UUID): Flow<List<House>>
    fun getAllByTerritory(territoryId: UUID): Flow<List<House>>
    fun getAllForTerritory(territoryId: UUID): Flow<List<House>>
    fun get(houseId: UUID): Flow<House>
    fun save(house: House): Flow<House>
    fun delete(house: House): Flow<House>
    fun deleteById(houseId: UUID): Flow<UUID>
    suspend fun deleteAll()

    fun getNextHouseNum(streetId: UUID): Flow<Int>
    fun clearTerritory(houseId: UUID): Flow<UUID>
}