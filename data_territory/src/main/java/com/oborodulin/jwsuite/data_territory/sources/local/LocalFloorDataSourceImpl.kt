package com.oborodulin.jwsuite.data_territory.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_territory.local.db.dao.FloorDao
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalFloorDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalFloorDataSourceImpl @Inject constructor(
    private val floorDao: FloorDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalFloorDataSource {
    override fun getAllFloors() = floorDao.findAll()
    override fun getHouseFloors(houseId: UUID) = floorDao.findByHouseId(houseId)
    override fun getEntranceFloors(entranceId: UUID) = floorDao.findByEntranceId(entranceId)

    override fun getTerritoryFloors(territoryId: UUID) = floorDao.findByTerritoryId(territoryId)

    override fun getFloorsForTerritory(territoryId: UUID) =
        floorDao.findByTerritoryMicrodistrictAndTerritoryLocalityDistrictAndTerritoryIdIsNull(
            territoryId
        )

    override fun getFloor(floorId: UUID) = floorDao.findDistinctById(floorId)

    override fun isHouseExistsRooms(houseId: UUID) = floorDao.existsByHouseId(houseId)
    override fun isEntranceExistsRooms(entranceId: UUID) = floorDao.existsByEntranceId(entranceId)
    override suspend fun insertFloor(floor: FloorEntity) = withContext(dispatcher) {
        floorDao.insert(floor)
    }

    override suspend fun updateFloor(floor: FloorEntity) = withContext(dispatcher) {
        floorDao.update(floor)
    }

    override suspend fun deleteFloor(floor: FloorEntity) = withContext(dispatcher) {
        floorDao.delete(floor)
    }

    override suspend fun deleteFloorById(floorId: UUID) = withContext(dispatcher) {
        floorDao.deleteById(floorId)
    }

    override suspend fun deleteFloors(floors: List<FloorEntity>) =
        withContext(dispatcher) {
            floorDao.delete(floors)
        }

    override suspend fun deleteAllFloors() = withContext(dispatcher) {
        floorDao.deleteAll()
    }

    override fun getNextEntranceNum(houseId: UUID) = floorDao.getNextHouseNum(houseId)

    override suspend fun clearTerritoryById(floorId: UUID) = withContext(dispatcher) {
        floorDao.clearTerritoryById(floorId)
    }

    override suspend fun setTerritoryById(floorId: UUID, territoryId: UUID) =
        withContext(dispatcher) {
            floorDao.updateTerritoryIdById(floorId, territoryId)
        }
}
