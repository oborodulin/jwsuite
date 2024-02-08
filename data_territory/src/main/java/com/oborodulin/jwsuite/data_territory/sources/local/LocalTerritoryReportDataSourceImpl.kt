package com.oborodulin.jwsuite.data_territory.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_territory.local.db.dao.TerritoryReportDao
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryReportDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalTerritoryReportDataSourceImpl @Inject constructor(
    private val territoryReportDao: TerritoryReportDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalTerritoryReportDataSource {
    override fun getTerritoryReports() = territoryReportDao.findDistinctAll()
    override fun getTerritoryReport(territoryReportId: UUID) =
        territoryReportDao.findDistinctById(territoryReportId)

    override fun getTerritoryStreetReports(territoryStreetId: UUID) =
        territoryReportDao.findByTerritoryStreetId(territoryStreetId)

    override fun getHouseTerritoryReports(houseId: UUID) = territoryReportDao.findByHouseId(houseId)
    override fun getRoomTerritoryReports(roomId: UUID) = territoryReportDao.findByRoomId(roomId)
    override fun getTerritoryReportStreet(territoryStreetId: UUID) =
        territoryReportDao.findReportStreetByTerritoryStreetId(territoryStreetId)

    override fun getTerritoryReportStreets(territoryId: UUID) =
        territoryReportDao.findReportStreetsByTerritoryId(territoryId)

    override fun getTerritoryReportHouse(houseId: UUID) =
        territoryReportDao.findReportHouseByHouseId(houseId)

    override fun getTerritoryReportHouses(territoryId: UUID, territoryStreetId: UUID?) =
        territoryReportDao.findReportHousesByTerritoryIdAndTerritoryStreetId(
            territoryId, territoryStreetId
        )

    override fun getTerritoryReportRoom(roomId: UUID) =
        territoryReportDao.findDistinctReportRoomByRoomId(roomId)

    override fun getTerritoryReportRooms(territoryId: UUID, houseId: UUID?) =
        territoryReportDao.findReportRoomsByTerritoryIdAndHouseId(territoryId, houseId)

    override suspend fun process(territoryReportId: UUID) =
        withContext(dispatcher) {
            territoryReportDao.updateIsReportProcessed(territoryReportId, true)
        }

    override suspend fun cancelProcess(territoryReportId: UUID) =
        withContext(dispatcher) {
            territoryReportDao.updateIsReportProcessed(territoryReportId, false)
        }

    override suspend fun insertTerritoryReport(territoryReport: TerritoryMemberReportEntity) =
        withContext(dispatcher) {
            territoryReportDao.insert(territoryReport)
        }

    override suspend fun updateTerritoryReport(territoryReport: TerritoryMemberReportEntity) =
        withContext(dispatcher) {
            territoryReportDao.update(territoryReport)
        }

    override suspend fun deleteTerritoryReport(territoryReport: TerritoryMemberReportEntity) =
        withContext(dispatcher) {
            territoryReportDao.delete(territoryReport)
        }

    override suspend fun deleteTerritoryReportById(territoryReportId: UUID) =
        withContext(dispatcher) {
            territoryReportDao.deleteById(territoryReportId)
        }

    override suspend fun deleteTerritoryReports(territoryReports: List<TerritoryMemberReportEntity>) =
        withContext(dispatcher) {
            territoryReportDao.delete(territoryReports)
        }

    override suspend fun deleteAllTerritoryReports() = withContext(dispatcher) {
        territoryReportDao.deleteAll()
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    override fun getTerritoryReportEntities(username: String?, byFavorite: Boolean) =
        territoryReportDao.selectEntities(username, byFavorite)

    override suspend fun loadTerritoryReportEntities(territoryReports: List<TerritoryMemberReportEntity>) =
        withContext(dispatcher) {
            territoryReportDao.insert(territoryReports)
        }
}
