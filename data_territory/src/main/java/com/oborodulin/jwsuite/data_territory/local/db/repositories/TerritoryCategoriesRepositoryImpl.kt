package com.oborodulin.jwsuite.data_territory.local.db.repositories

import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.category.TerritoryCategoryMappers
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryCategoryDataSource
import com.oborodulin.jwsuite.domain.model.TerritoryCategory
import com.oborodulin.jwsuite.domain.repositories.TerritoryCategoriesRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class TerritoryCategoriesRepositoryImpl @Inject constructor(
    private val localTerritoryCategoryDataSource: LocalTerritoryCategoryDataSource,
    private val mappers: TerritoryCategoryMappers
) : TerritoryCategoriesRepository {
    override fun getAll() = localTerritoryCategoryDataSource.getTerritoryCategories()
        .map(mappers.territoryCategoryEntityListToTerritoryCategoriesListMapper::map)

    override fun get(territoryCategoryId: UUID) =
        localTerritoryCategoryDataSource.getTerritoryCategory(territoryCategoryId)
            .map(mappers.territoryCategoryEntityToTerritoryCategoryMapper::map)

    override fun save(territoryCategory: TerritoryCategory) = flow {
        if (territoryCategory.id == null) {
            localTerritoryCategoryDataSource.insertTerritoryCategory(
                mappers.territoryCategoryToTerritoryCategoryEntityMapper.map(territoryCategory),
            )
        } else {
            localTerritoryCategoryDataSource.updateTerritoryCategory(
                mappers.territoryCategoryToTerritoryCategoryEntityMapper.map(territoryCategory),
            )
        }
        emit(territoryCategory)
    }

    override fun delete(territoryCategory: TerritoryCategory) = flow {
        localTerritoryCategoryDataSource.deleteTerritoryCategory(
            mappers.territoryCategoryToTerritoryCategoryEntityMapper.map(territoryCategory)
        )
        this.emit(territoryCategory)
    }

    override fun deleteById(territoryCategoryId: UUID) = flow {
        localTerritoryCategoryDataSource.deleteTerritoryCategoryById(territoryCategoryId)
        this.emit(territoryCategoryId)
    }

    override suspend fun deleteAll() =
        localTerritoryCategoryDataSource.deleteAllTerritoryCategories()

}