package com.oborodulin.jwsuite.data_territory.local.db.repositories.sources

import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryMemberReportView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportHouseView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportRoomView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalTerritoryReportDataSource {
    fun getTerritoryReports(): Flow<List<TerritoryMemberReportEntity>>
    fun getTerritoryReport(territoryReportId: UUID): Flow<TerritoryMemberReportEntity>
    fun getTerritoryStreetReports(territoryStreetId: UUID): Flow<List<TerritoryMemberReportView>>
    fun getHouseTerritoryReports(houseId: UUID): Flow<List<TerritoryMemberReportView>>
    fun getRoomTerritoryReports(roomId: UUID): Flow<List<TerritoryMemberReportView>>
    fun getTerritoryReportHouses(
        territoryId: UUID, territoryStreetId: UUID? = null
    ): Flow<List<TerritoryReportHouseView>>

    fun getTerritoryReportRooms(
        territoryId: UUID, houseId: UUID? = null
    ): Flow<List<TerritoryReportRoomView>>

    suspend fun insertTerritoryReport(territoryReport: TerritoryMemberReportEntity)
    suspend fun updateTerritoryReport(territoryReport: TerritoryMemberReportEntity)
    suspend fun deleteTerritoryReport(territoryReport: TerritoryMemberReportEntity)
    suspend fun deleteTerritoryReportById(territoryReportId: UUID)
    suspend fun deleteTerritoryReports(territoryReports: List<TerritoryMemberReportEntity>)
    suspend fun deleteAllTerritoryReports()
}