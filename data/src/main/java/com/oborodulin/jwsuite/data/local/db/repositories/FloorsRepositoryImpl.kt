package com.oborodulin.jwsuite.data.local.db.repositories

import com.oborodulin.jwsuite.data.local.db.mappers.floor.FloorMappers
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalFloorDataSource
import com.oborodulin.jwsuite.domain.model.Floor
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
        .map(mappers.floorEntityListToFloorsListMapper::map)

    override fun getAllByHouse(houseId: UUID) = localFloorDataSource.getHouseFloors(houseId)
        .map(mappers.floorEntityListToFloorsListMapper::map)

    override fun getAllByEntrance(entranceId: UUID) =
        localFloorDataSource.getEntranceFloors(entranceId)
            .map(mappers.floorEntityListToFloorsListMapper::map)

    override fun getAllByTerritory(territoryId: UUID) =
        localFloorDataSource.getTerritoryFloors(territoryId)
            .map(mappers.floorEntityListToFloorsListMapper::map)

    override fun get(floorId: UUID) = localFloorDataSource.getFloor(floorId)
        .map(mappers.floorEntityToFloorMapper::map)

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
}