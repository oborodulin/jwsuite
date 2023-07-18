package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data.local.db.entities.TerritoryCategoryEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalTerritoryCategoryDataSource {
    fun getTerritoryCategories(): Flow<List<TerritoryCategoryEntity>>
    fun getTerritoryCategory(territoryCategoryId: UUID): Flow<TerritoryCategoryEntity>
    suspend fun insertTerritoryCategory(territoryCategory: TerritoryCategoryEntity)
    suspend fun updateTerritoryCategory(territoryCategory: TerritoryCategoryEntity)
    suspend fun deleteTerritoryCategory(territoryCategory: TerritoryCategoryEntity)
    suspend fun deleteTerritoryCategoryById(territoryCategoryId: UUID)
    suspend fun deleteTerritoryCategories(territoryCategories: List<TerritoryCategoryEntity>)
    suspend fun deleteAllTerritoryCategories()
}