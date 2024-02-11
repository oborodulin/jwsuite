package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.territory.TerritoryCategory
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryCategoryCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TerritoryCategoriesRepository : CsvTransferableRepo {
    fun getAll(): Flow<List<TerritoryCategory>>
    fun get(territoryCategoryId: UUID): Flow<TerritoryCategory>
    fun save(territoryCategory: TerritoryCategory): Flow<TerritoryCategory>
    fun delete(territoryCategory: TerritoryCategory): Flow<TerritoryCategory>
    fun delete(territoryCategoryId: UUID): Flow<UUID>
    suspend fun deleteAll()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractTerritoryCategories(): Flow<List<TerritoryCategoryCsv>>
    fun loadTerritoryCategories(territoryCategories: List<TerritoryCategoryCsv>): Flow<Int>
}