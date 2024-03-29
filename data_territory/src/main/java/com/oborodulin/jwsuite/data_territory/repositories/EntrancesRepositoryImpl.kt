package com.oborodulin.jwsuite.data_territory.repositories

import com.oborodulin.jwsuite.data_territory.local.csv.mappers.entrance.EntranceCsvMappers
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance.EntranceMappers
import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalEntranceDataSource
import com.oborodulin.jwsuite.domain.model.territory.Entrance
import com.oborodulin.jwsuite.domain.repositories.EntrancesRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.territory.EntranceCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class EntrancesRepositoryImpl @Inject constructor(
    private val localEntranceDataSource: LocalEntranceDataSource,
    private val domainMappers: EntranceMappers,
    private val csvMappers: EntranceCsvMappers
) : EntrancesRepository {
    override fun getAll() = localEntranceDataSource.getAllEntrances()
        .map(domainMappers.entranceViewListToEntrancesListMapper::map)

    override fun getAllByHouse(houseId: UUID) = localEntranceDataSource.getHouseEntrances(houseId)
        .map(domainMappers.entranceViewListToEntrancesListMapper::map)

    override fun getAllByTerritory(territoryId: UUID) =
        localEntranceDataSource.getTerritoryEntrances(territoryId)
            .map(domainMappers.entranceViewListToEntrancesListMapper::map)

    override fun getAllForTerritory(territoryId: UUID) =
        localEntranceDataSource.getEntrancesForTerritory(territoryId)
            .map(domainMappers.entranceViewListToEntrancesListMapper::map)

    override fun getNext(entranceId: UUID) = localEntranceDataSource.getNextEntrance(entranceId)
        .map(domainMappers.entranceViewToEntranceMapper::nullableMap)

    override fun get(entranceId: UUID) = localEntranceDataSource.getEntrance(entranceId)
        .map(domainMappers.entranceViewToEntranceMapper::map)

    override fun isExistsInHouse(houseId: UUID) =
        localEntranceDataSource.isHouseExistsRooms(houseId)

    override fun save(entrance: Entrance) = flow {
        if (entrance.id == null) {
            localEntranceDataSource.insertEntrance(
                domainMappers.entranceToEntranceEntityMapper.map(entrance)
            )
        } else {
            localEntranceDataSource.updateEntrance(
                domainMappers.entranceToEntranceEntityMapper.map(entrance)
            )
        }
        emit(entrance)
    }

    override fun delete(entrance: Entrance) = flow {
        localEntranceDataSource.deleteEntrance(
            domainMappers.entranceToEntranceEntityMapper.map(
                entrance
            )
        )
        this.emit(entrance)
    }

    override fun delete(entranceId: UUID) = flow {
        localEntranceDataSource.deleteEntranceById(entranceId)
        this.emit(entranceId)
    }

    override suspend fun deleteAll() = localEntranceDataSource.deleteAllEntrances()

    override fun getNextNum(houseId: UUID) = flow {
        emit(localEntranceDataSource.getNextEntranceNum(houseId))
    }

    override fun clearTerritory(entranceId: UUID) = flow {
        localEntranceDataSource.clearTerritoryById(entranceId)
        this.emit(entranceId)
    }

    override fun setTerritory(entranceIds: List<UUID>, territoryId: UUID) = flow {
        entranceIds.forEach { localEntranceDataSource.setTerritoryById(it, territoryId) }
        this.emit(entranceIds)
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = EntranceEntity.TABLE_NAME)
    override fun extractEntrances(username: String?, byFavorite: Boolean) =
        localEntranceDataSource.getEntranceEntities(username, byFavorite)
            .map(csvMappers.entranceEntityListToEntranceCsvListMapper::map)

    @CsvLoad<EntranceCsv>(
        fileNamePrefix = EntranceEntity.TABLE_NAME,
        contentType = EntranceCsv::class
    )
    override fun loadEntrances(entrances: List<EntranceCsv>) = flow {
        localEntranceDataSource.loadEntranceEntities(
            csvMappers.entranceCsvListToEntranceEntityListMapper.map(entrances)
        )
        emit(entrances.size)
    }
}