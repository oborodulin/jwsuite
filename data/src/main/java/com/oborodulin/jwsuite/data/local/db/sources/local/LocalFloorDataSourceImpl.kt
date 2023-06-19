package com.oborodulin.jwsuite.data.local.db.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.FloorDao
import com.oborodulin.jwsuite.data.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalFloorDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalFloorDataSourceImpl @Inject constructor(
    private val floorDao: FloorDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalFloorDataSource {
    override fun getHouseFloors(entranceId: UUID) = floorDao.findByEntranceId(entranceId)

    override fun getTerritoryFloors(territoryId: UUID) = floorDao.findByTerritoryId(territoryId)

    override fun getFloor(floorId: UUID) = floorDao.findDistinctById(floorId)

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

}
