package com.oborodulin.jwsuite.data_territory.local.db.repositories

import com.oborodulin.jwsuite.data_territory.local.db.mappers.floor.FloorMappers
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalFloorDataSource
import com.oborodulin.jwsuite.domain.model.territory.Floor
import com.oborodulin.jwsuite.domain.repositories.FloorsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class FloorsRepositoryImpl @Inject constructor(
    private val localFloorDataSource: LocalFloorDataSource,
    private val mappers: FloorMappers
) : FloorsRepository {
    override fun getAll() = localFloorDataSource.getAllFloors()
        .map(mappers.floorViewListToFloorsListMapper::map)

    override fun getAllByHouse(houseId: UUID) = localFloorDataSource.getHouseFloors(houseId)
        .map(mappers.floorViewListToFloorsListMapper::map)

    override fun getAllByEntrance(entranceId: UUID) =
        localFloorDataSource.getEntranceFloors(entranceId)
            .map(mappers.floorViewListToFloorsListMapper::map)

    override fun getAllByTerritory(territoryId: UUID) =
        localFloorDataSource.getTerritoryFloors(territoryId)
            .map(mappers.floorViewListToFloorsListMapper::map)

    override fun getAllForTerritory(territoryId: UUID) =
        localFloorDataSource.getFloorsForTerritory(territoryId)
            .map(mappers.floorViewListToFloorsListMapper::map)

    override fun get(floorId: UUID) = localFloorDataSource.getFloor(floorId)
        .map(mappers.floorViewToFloorMapper::map)

    override fun save(floor: Floor) = flow {
        if (floor.id == null) {
            localFloorDataSource.insertFloor(mappers.floorToFloorEntityMapper.map(floor))
        } else {
            localFloorDataSource.updateFloor(mappers.floorToFloorEntityMapper.map(floor))
        }
        emit(floor)
    }

    override fun delete(floor: Floor) = flow {
        localFloorDataSource.deleteFloor(mappers.floorToFloorEntityMapper.map(floor))
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
}