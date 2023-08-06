package com.oborodulin.jwsuite.data.local.db.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.HouseDao
import com.oborodulin.jwsuite.data.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalHouseDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalHouseDataSourceImpl @Inject constructor(
    private val houseDao: HouseDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalHouseDataSource {
    override fun getAllHouses() = houseDao.findAll()
    override fun getStreetHouses(streetId: UUID) = houseDao.findByStreetId(streetId)
    override fun getTerritoryHouses(territoryId: UUID) = houseDao.findByTerritoryId(territoryId)
    override fun getTerritoryStreetHouses(territoryId: UUID) =
        houseDao.findOnTerritoryStreetsByTerritoryId(territoryId)

    override fun getHouse(houseId: UUID) = houseDao.findDistinctById(houseId)

    override suspend fun insertHouse(house: HouseEntity) = withContext(dispatcher) {
        houseDao.insert(house)
    }

    override suspend fun updateHouse(house: HouseEntity) = withContext(dispatcher) {
        houseDao.update(house)
    }

    override suspend fun deleteHouse(house: HouseEntity) = withContext(dispatcher) {
        houseDao.delete(house)
    }

    override suspend fun deleteHouseById(houseId: UUID) = withContext(dispatcher) {
        houseDao.deleteById(houseId)
    }

    override suspend fun deleteHouses(houses: List<HouseEntity>) = withContext(dispatcher) {
        houseDao.delete(houses)
    }

    override suspend fun deleteAllHouses() = withContext(dispatcher) {
        houseDao.deleteAll()
    }

}
