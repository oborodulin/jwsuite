package com.oborodulin.jwsuite.data_territory.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_territory.local.db.dao.TerritoryStreetDao
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryStreetDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalTerritoryStreetDataSourceImpl @Inject constructor(
    private val territoryStreetDao: TerritoryStreetDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalTerritoryStreetDataSource {
    override fun getTerritoryStreet(territoryStreetId: UUID) =
        territoryStreetDao.findTerritoryStreetById(territoryStreetId)

    override fun getTerritoryStreets(territoryId: UUID) =
        territoryStreetDao.findStreetsByTerritoryId(territoryId)

    override fun getStreetsForTerritory(territoryId: UUID) =
        territoryStreetDao.findStreetsForTerritoryByTerritoryId(territoryId)

    override fun getTerritoryStreetNamesAndHouseNums(congregationId: UUID?) =
        territoryStreetDao.findStreetNamesAndHouseNumsByCongregationId(congregationId)
    /*    override fun getTerritoryStreetNames(territoryId: UUID) =
            territoryDao.findNamesByTerritoryId(territoryId)*/

    override suspend fun insertStreet(
        territory: TerritoryEntity, street: GeoStreetEntity,
        isEvenSide: Boolean?, isPrivateSector: Boolean?, estimatedHouses: Int?
    ) = withContext(dispatcher) {
        territoryStreetDao.insert(territory, street, isEvenSide, isPrivateSector, estimatedHouses)
    }

    override suspend fun insertStreet(territoryStreet: TerritoryStreetEntity) =
        withContext(dispatcher) {
            territoryStreetDao.insert(territoryStreet)
        }

    override suspend fun updateStreet(territoryStreet: TerritoryStreetEntity) =
        withContext(dispatcher) { territoryStreetDao.update(territoryStreet) }

    override suspend fun deleteStreet(territoryStreet: TerritoryStreetEntity) =
        withContext(dispatcher) { territoryStreetDao.deleteStreet(territoryStreet) }

    override suspend fun deleteStreet(territoryStreetId: UUID) = withContext(dispatcher) {
        territoryStreetDao.deleteStreetById(territoryStreetId)
    }

    override suspend fun deleteStreets(territoryId: UUID) = withContext(dispatcher) {
        territoryStreetDao.deleteStreetsByTerritoryId(territoryId)
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    override fun getTerritoryStreetEntities(username: String?, byFavorite: Boolean) =
        territoryStreetDao.findEntitiesByUsernameAndFavoriteMark(username, byFavorite)

    override suspend fun loadTerritoryStreetEntities(territoryStreets: List<TerritoryStreetEntity>) =
        withContext(dispatcher) {
            territoryStreetDao.insert(territoryStreets)
        }
}
