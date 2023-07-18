package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.TerritoryCategory
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TerritoryCategoriesRepository {
    fun getAll(): Flow<List<TerritoryCategory>>
    fun get(territoryCategoryId: UUID): Flow<TerritoryCategory>
    fun save(territoryCategory: TerritoryCategory): Flow<TerritoryCategory>
    fun delete(territoryCategory: TerritoryCategory): Flow<TerritoryCategory>
    fun deleteById(territoryCategoryId: UUID): Flow<UUID>
    suspend fun deleteAll()
}