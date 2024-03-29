package com.oborodulin.jwsuite.data_territory.repositories

import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorycategory.TerritoryCategoryCsvMappers
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territorycategory.TerritoryCategoryMappers
import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalTerritoryCategoryDataSource
import com.oborodulin.jwsuite.domain.model.territory.TerritoryCategory
import com.oborodulin.jwsuite.domain.repositories.TerritoryCategoriesRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryCategoryCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class TerritoryCategoriesRepositoryImpl @Inject constructor(
    private val localTerritoryCategoryDataSource: LocalTerritoryCategoryDataSource,
    private val domainMappers: TerritoryCategoryMappers,
    private val csvMappers: TerritoryCategoryCsvMappers
) : TerritoryCategoriesRepository {
    override fun getAll() = localTerritoryCategoryDataSource.getTerritoryCategories()
        .map(domainMappers.territoryCategoryEntityListToTerritoryCategoriesListMapper::map)

    override fun get(territoryCategoryId: UUID) =
        localTerritoryCategoryDataSource.getTerritoryCategory(territoryCategoryId)
            .map(domainMappers.territoryCategoryEntityToTerritoryCategoryMapper::map)

    override fun save(territoryCategory: TerritoryCategory) = flow {
        if (territoryCategory.id == null) {
            localTerritoryCategoryDataSource.insertTerritoryCategory(
                domainMappers.territoryCategoryToTerritoryCategoryEntityMapper.map(territoryCategory),
            )
        } else {
            localTerritoryCategoryDataSource.updateTerritoryCategory(
                domainMappers.territoryCategoryToTerritoryCategoryEntityMapper.map(territoryCategory),
            )
        }
        emit(territoryCategory)
    }

    override fun delete(territoryCategory: TerritoryCategory) = flow {
        localTerritoryCategoryDataSource.deleteTerritoryCategory(
            domainMappers.territoryCategoryToTerritoryCategoryEntityMapper.map(territoryCategory)
        )
        this.emit(territoryCategory)
    }

    override fun delete(territoryCategoryId: UUID) = flow {
        localTerritoryCategoryDataSource.deleteTerritoryCategoryById(territoryCategoryId)
        this.emit(territoryCategoryId)
    }

    override suspend fun deleteAll() =
        localTerritoryCategoryDataSource.deleteAllTerritoryCategories()

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = TerritoryCategoryEntity.TABLE_NAME)
    override fun extractTerritoryCategories() =
        localTerritoryCategoryDataSource.getTerritoryCategoryEntities()
            .map(csvMappers.territoryCategoryEntityListToTerritoryCategoryCsvListMapper::map)

    @CsvLoad<TerritoryCategoryCsv>(
        fileNamePrefix = TerritoryCategoryEntity.TABLE_NAME,
        contentType = TerritoryCategoryCsv::class
    )
    override fun loadTerritoryCategories(territoryCategories: List<TerritoryCategoryCsv>) = flow {
        localTerritoryCategoryDataSource.loadTerritoryCategoryEntities(
            csvMappers.territoryCategoryCsvListToTerritoryCategoryEntityListMapper.map(
                territoryCategories
            )
        )
        emit(territoryCategories.size)
    }
}