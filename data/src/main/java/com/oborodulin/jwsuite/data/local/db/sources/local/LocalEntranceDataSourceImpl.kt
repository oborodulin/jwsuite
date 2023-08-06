package com.oborodulin.jwsuite.data.local.db.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.EntranceDao
import com.oborodulin.jwsuite.data.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalEntranceDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.*
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

    override fun getEntrance(entranceId: UUID) = entranceDao.findDistinctById(entranceId)

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

}
