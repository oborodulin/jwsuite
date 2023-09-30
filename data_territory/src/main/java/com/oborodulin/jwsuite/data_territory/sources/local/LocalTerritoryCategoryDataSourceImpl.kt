package com.oborodulin.jwsuite.data_territory.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_territory.local.db.dao.TerritoryCategoryDao
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryCategoryDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalTerritoryCategoryDataSourceImpl @Inject constructor(
    private val territoryCategoryDao: TerritoryCategoryDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalTerritoryCategoryDataSource {
    override fun getTerritoryCategories() = territoryCategoryDao.findDistinctAll()
    override fun getTerritoryCategory(territoryCategoryId: UUID) =
        territoryCategoryDao.findDistinctById(territoryCategoryId)

    override suspend fun insertTerritoryCategory(territoryCategory: TerritoryCategoryEntity) =
        withContext(dispatcher) {
            territoryCategoryDao.insert(territoryCategory)
        }

    override suspend fun updateTerritoryCategory(territoryCategory: TerritoryCategoryEntity) =
        withContext(dispatcher) {
            territoryCategoryDao.update(territoryCategory)
        }

    override suspend fun deleteTerritoryCategory(territoryCategory: TerritoryCategoryEntity) =
        withContext(dispatcher) {
            territoryCategoryDao.delete(territoryCategory)
        }

    override suspend fun deleteTerritoryCategoryById(territoryCategoryId: UUID) = withContext(dispatcher) {
        territoryCategoryDao.deleteById(territoryCategoryId)
    }

    override suspend fun deleteTerritoryCategories(territoryCategories: List<TerritoryCategoryEntity>) =
        withContext(dispatcher) {
            territoryCategoryDao.delete(territoryCategories)
        }

    override suspend fun deleteAllTerritoryCategories() = withContext(dispatcher) {
        territoryCategoryDao.deleteAll()
    }
}
