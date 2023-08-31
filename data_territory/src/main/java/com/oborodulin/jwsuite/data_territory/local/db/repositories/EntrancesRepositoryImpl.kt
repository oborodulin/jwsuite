package com.oborodulin.jwsuite.data_territory.local.db.repositories

import com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance.EntranceMappers
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.local.LocalEntranceDataSource
import com.oborodulin.jwsuite.domain.model.Entrance
import com.oborodulin.jwsuite.domain.repositories.EntrancesRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class EntrancesRepositoryImpl @Inject constructor(
    private val localEntranceDataSource: LocalEntranceDataSource,
    private val mappers: EntranceMappers
) : EntrancesRepository {
    override fun getAll() = localEntranceDataSource.getAllEntrances()
        .map(mappers.entranceViewListToEntrancesListMapper::map)

    override fun getAllByHouse(houseId: UUID) = localEntranceDataSource.getHouseEntrances(houseId)
        .map(mappers.entranceViewListToEntrancesListMapper::map)

    override fun getAllByTerritory(territoryId: UUID) =
        localEntranceDataSource.getTerritoryEntrances(territoryId)
            .map(mappers.entranceViewListToEntrancesListMapper::map)

    override fun get(entranceId: UUID) = localEntranceDataSource.getEntrance(entranceId)
        .map(mappers.entranceViewToEntranceMapper::map)

    override fun save(entrance: Entrance) = flow {
        if (entrance.id == null) {
            localEntranceDataSource.insertEntrance(
                mappers.entranceToEntranceEntityMapper.map(entrance)
            )
        } else {
            localEntranceDataSource.updateEntrance(
                mappers.entranceToEntranceEntityMapper.map(entrance)
            )
        }
        emit(entrance)
    }

    override fun delete(entrance: Entrance) = flow {
        localEntranceDataSource.deleteEntrance(mappers.entranceToEntranceEntityMapper.map(entrance))
        this.emit(entrance)
    }

    override fun deleteById(entranceId: UUID) = flow {
        localEntranceDataSource.deleteEntranceById(entranceId)
        this.emit(entranceId)
    }

    override suspend fun deleteAll() = localEntranceDataSource.deleteAllEntrances()
}