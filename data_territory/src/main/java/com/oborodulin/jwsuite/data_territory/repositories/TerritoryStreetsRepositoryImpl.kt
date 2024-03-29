package com.oborodulin.jwsuite.data_territory.repositories

import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorystreet.TerritoryStreetCsvMappers
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territorystreet.TerritoryStreetMappers
import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalTerritoryStreetDataSource
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreet
import com.oborodulin.jwsuite.domain.repositories.TerritoryStreetsRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryStreetCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class TerritoryStreetsRepositoryImpl @Inject constructor(
    private val localTerritoryDataSource: LocalTerritoryStreetDataSource,
    private val domainMappers: TerritoryStreetMappers,
    private val csvMappers: TerritoryStreetCsvMappers
) : TerritoryStreetsRepository {
    override fun get(territoryStreetId: UUID) =
        localTerritoryDataSource.getTerritoryStreet(territoryStreetId)
            .map(domainMappers.territoryStreetViewToTerritoryStreetMapper::map)

    override fun getAllByTerritory(territoryId: UUID) =
        localTerritoryDataSource.getTerritoryStreets(territoryId)
            .map(domainMappers.territoryStreetViewListToTerritoryStreetsListMapper::map)

    override fun getGeoStreetsForTerritory(territoryId: UUID) =
        localTerritoryDataSource.getStreetsForTerritory(territoryId)
            .map(domainMappers.streetViewListToGeoStreetsListMapper::map)

    override fun getTerritoryStreetNamesAndHouseNums(congregationId: UUID?) =
        localTerritoryDataSource.getTerritoryStreetNamesAndHouseNums(congregationId)
            .map(domainMappers.territoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper::map)

    override fun save(territoryStreet: TerritoryStreet) = flow {
        if (territoryStreet.id == null) {
            localTerritoryDataSource.insertStreet(
                domainMappers.territoryStreetToTerritoryStreetEntityMapper.map(territoryStreet),
            )
        } else {
            localTerritoryDataSource.updateStreet(
                domainMappers.territoryStreetToTerritoryStreetEntityMapper.map(territoryStreet),
            )
        }
        emit(territoryStreet)
    }

    override fun delete(territoryStreetId: UUID) = flow {
        localTerritoryDataSource.deleteStreet(territoryStreetId)
        this.emit(territoryStreetId)
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = TerritoryStreetEntity.TABLE_NAME)
    override fun extractTerritoryStreets(username: String?, byFavorite: Boolean) =
        localTerritoryDataSource.getTerritoryStreetEntities(username, byFavorite)
            .map(csvMappers.territoryStreetEntityListToTerritoryStreetCsvListMapper::map)

    @CsvLoad<TerritoryStreetCsv>(
        fileNamePrefix = TerritoryStreetEntity.TABLE_NAME,
        contentType = TerritoryStreetCsv::class
    )
    override fun loadTerritoryStreets(territoryStreets: List<TerritoryStreetCsv>) = flow {
        localTerritoryDataSource.loadTerritoryStreetEntities(
            csvMappers.territoryStreetCsvListToTerritoryStreetEntityListMapper.map(territoryStreets)
        )
        emit(territoryStreets.size)
    }
}