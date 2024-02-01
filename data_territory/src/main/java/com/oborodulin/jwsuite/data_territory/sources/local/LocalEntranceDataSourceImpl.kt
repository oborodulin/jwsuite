package com.oborodulin.jwsuite.data_territory.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_territory.local.db.dao.EntranceDao
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalEntranceDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalEntranceDataSourceImpl @Inject constructor(
    private val entranceDao: EntranceDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalEntranceDataSource {
    override fun getAllEntrances() = entranceDao.findAll()
    override fun getHouseEntrances(houseId: UUID) = entranceDao.findByHouseId(houseId)

    override fun getTerritoryEntrances(territoryId: UUID) =
        entranceDao.findByTerritoryId(territoryId)

    override fun getEntrancesForTerritory(territoryId: UUID) =
        entranceDao.findByTerritoryMicrodistrictAndTerritoryLocalityDistrictAndTerritoryIdIsNull(
            territoryId
        )

    override fun getEntrance(entranceId: UUID) = entranceDao.findDistinctById(entranceId)

    override fun isHouseExistsRooms(houseId: UUID) = entranceDao.existsByHouseId(houseId)
    override suspend fun insertEntrance(entrance: EntranceEntity) = withContext(dispatcher) {
        entranceDao.insert(entrance)
    }

    override suspend fun updateEntrance(entrance: EntranceEntity) = withContext(dispatcher) {
        entranceDao.update(entrance)
    }

    override suspend fun deleteEntrance(entrance: EntranceEntity) = withContext(dispatcher) {
        entranceDao.delete(entrance)
    }

    override suspend fun deleteEntranceById(entranceId: UUID) = withContext(dispatcher) {
        entranceDao.deleteById(entranceId)
    }

    override suspend fun deleteEntrances(entrances: List<EntranceEntity>) =
        withContext(dispatcher) {
            entranceDao.delete(entrances)
        }

    override suspend fun deleteAllEntrances() = withContext(dispatcher) {
        entranceDao.deleteAll()
    }

    override fun getNextEntranceNum(houseId: UUID) = entranceDao.getNextHouseNum(houseId)

    override suspend fun clearTerritoryById(entranceId: UUID) = withContext(dispatcher) {
        entranceDao.clearTerritoryById(entranceId)
    }

    override suspend fun setTerritoryById(entranceId: UUID, territoryId: UUID) =
        withContext(dispatcher) {
            entranceDao.updateTerritoryIdById(entranceId, territoryId)
        }

    // -------------------------------------- CSV Transfer --------------------------------------
    override fun getEntranceEntities() = entranceDao.selectEntities()
    override suspend fun loadEntranceEntities(entrances: List<EntranceEntity>) =
        withContext(dispatcher) {
            entranceDao.insert(entrances)
        }
}
