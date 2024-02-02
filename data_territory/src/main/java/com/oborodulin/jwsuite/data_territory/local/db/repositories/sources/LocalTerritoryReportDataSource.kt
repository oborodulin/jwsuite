package com.oborodulin.jwsuite.data_territory.local.db.repositories.sources

import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryMemberReportView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportHouseView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportRoomView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportStreetView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalTerritoryReportDataSource {
    fun getTerritoryReports(): Flow<List<TerritoryMemberReportEntity>>
    fun getTerritoryReport(territoryReportId: UUID): Flow<TerritoryMemberReportEntity>
    fun getTerritoryStreetReports(territoryStreetId: UUID): Flow<List<TerritoryMemberReportView>>
    fun getHouseTerritoryReports(houseId: UUID): Flow<List<TerritoryMemberReportView>>
    fun getRoomTerritoryReports(roomId: UUID): Flow<List<TerritoryMemberReportView>>
    fun getTerritoryReportStreet(territoryStreetId: UUID): Flow<TerritoryReportStreetView>
    fun getTerritoryReportStreets(territoryId: UUID): Flow<List<TerritoryReportStreetView>>
    fun getTerritoryReportHouse(houseId: UUID): Flow<TerritoryReportHouseView>
    fun getTerritoryReportHouses(
        territoryId: UUID, territoryStreetId: UUID? = null
    ): Flow<List<TerritoryReportHouseView>>

    fun getTerritoryReportRoom(roomId: UUID): Flow<TerritoryReportRoomView>
    fun getTerritoryReportRooms(
        territoryId: UUID, houseId: UUID? = null
    ): Flow<List<TerritoryReportRoomView>>

    suspend fun process(territoryReportId: UUID)
    suspend fun cancelProcess(territoryReportId: UUID)
    suspend fun insertTerritoryReport(territoryReport: TerritoryMemberReportEntity)
    suspend fun updateTerritoryReport(territoryReport: TerritoryMemberReportEntity)
    suspend fun deleteTerritoryReport(territoryReport: TerritoryMemberReportEntity)
    suspend fun deleteTerritoryReportById(territoryReportId: UUID)
    suspend fun deleteTerritoryReports(territoryReports: List<TerritoryMemberReportEntity>)
    suspend fun deleteAllTerritoryReports()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun getTerritoryReportEntities(): Flow<List<TerritoryMemberReportEntity>>
    suspend fun loadTerritoryReportEntities(territoryReports: List<TerritoryMemberReportEntity>)
}