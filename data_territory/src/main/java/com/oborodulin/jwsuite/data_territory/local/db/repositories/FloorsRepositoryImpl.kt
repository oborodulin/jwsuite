package com.oborodulin.jwsuite.data_territory.local.db.repositories

import com.oborodulin.jwsuite.data_territory.local.csv.mappers.floor.FloorCsvMappers
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data_territory.local.db.mappers.floor.FloorMappers
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalFloorDataSource
import com.oborodulin.jwsuite.domain.model.territory.Floor
import com.oborodulin.jwsuite.domain.repositories.FloorsRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.territory.FloorCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class FloorsRepositoryImpl @Inject constructor(
    private val localFloorDataSource: LocalFloorDataSource,
    private val domainMappers: FloorMappers,
    private val csvMappers: FloorCsvMappers
) : FloorsRepository {
    override fun getAll() = localFloorDataSource.getAllFloors()
        .map(domainMappers.floorViewListToFloorsListMapper::map)

    override fun getAllByHouse(houseId: UUID) = localFloorDataSource.getHouseFloors(houseId)
        .map(domainMappers.floorViewListToFloorsListMapper::map)

    override fun getAllByEntrance(entranceId: UUID) =
        localFloorDataSource.getEntranceFloors(entranceId)
            .map(domainMappers.floorViewListToFloorsListMapper::map)

    override fun getAllByTerritory(territoryId: UUID) =
        localFloorDataSource.getTerritoryFloors(territoryId)
            .map(domainMappers.floorViewListToFloorsListMapper::map)

    override fun getAllForTerritory(territoryId: UUID) =
        localFloorDataSource.getFloorsForTerritory(territoryId)
            .map(domainMappers.floorViewListToFloorsListMapper::map)

    override fun getNext(floorId: UUID) = localFloorDataSource.getNextFloor(floorId)
        .map(domainMappers.floorViewToFloorMapper::nullableMap)

    override fun get(floorId: UUID) = localFloorDataSource.getFloor(floorId)
        .map(domainMappers.floorViewToFloorMapper::map)

    override fun isExistsInHouse(houseId: UUID) = localFloorDataSource.isHouseExistsRooms(houseId)
    override fun isExistsInEntrance(entranceId: UUID) =
        localFloorDataSource.isEntranceExistsRooms(entranceId)

    override fun save(floor: Floor) = flow {
        if (floor.id == null) {
            localFloorDataSource.insertFloor(domainMappers.floorToFloorEntityMapper.map(floor))
        } else {
            localFloorDataSource.updateFloor(domainMappers.floorToFloorEntityMapper.map(floor))
        }
        emit(floor)
    }

    override fun delete(floor: Floor) = flow {
        localFloorDataSource.deleteFloor(domainMappers.floorToFloorEntityMapper.map(floor))
        this.emit(floor)
    }

    override fun deleteById(floorId: UUID) = flow {
        localFloorDataSource.deleteFloorById(floorId)
        this.emit(floorId)
    }

    override suspend fun deleteAll() = localFloorDataSource.deleteAllFloors()

    override fun getNextNum(houseId: UUID) = flow {
        emit(localFloorDataSource.getNextEntranceNum(houseId))
    }

    override fun clearTerritory(floorId: UUID) = flow {
        localFloorDataSource.clearTerritoryById(floorId)
        this.emit(floorId)
    }

    override fun setTerritory(floorIds: List<UUID>, territoryId: UUID) = flow {
        floorIds.forEach { localFloorDataSource.setTerritoryById(it, territoryId) }
        this.emit(floorIds)
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = FloorEntity.TABLE_NAME)
    override fun extractFloors(username: String?, byFavorite: Boolean) =
        localFloorDataSource.getFloorEntities(username, byFavorite)
            .map(csvMappers.floorEntityListToFloorCsvListMapper::map)

    @CsvLoad<FloorCsv>(fileNamePrefix = FloorEntity.TABLE_NAME, contentType = FloorCsv::class)
    override fun loadFloors(floors: List<FloorCsv>) = flow {
        localFloorDataSource.loadFloorEntities(
            csvMappers.floorCsvListToFloorEntityListMapper.map(floors)
        )
        emit(floors.size)
    }
}