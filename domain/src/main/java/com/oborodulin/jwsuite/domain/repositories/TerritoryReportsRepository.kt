package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportHouse
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportRoom
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportStreet
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryMemberReportCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TerritoryReportsRepository : CsvTransferableRepo {
    fun getAll(): Flow<List<TerritoryMemberReport>>
    fun get(territoryReportId: UUID): Flow<TerritoryMemberReport>
    fun getAllByTerritoryStreet(territoryStreetId: UUID): Flow<List<TerritoryMemberReport>>
    fun getAllByHouse(houseId: UUID): Flow<List<TerritoryMemberReport>>
    fun getAllByRoom(roomId: UUID): Flow<List<TerritoryMemberReport>>
    fun getTerritoryReportStreet(territoryStreetId: UUID): Flow<TerritoryReportStreet>
    fun getTerritoryReportStreets(territoryId: UUID): Flow<List<TerritoryReportStreet>>
    fun getTerritoryReportHouse(houseId: UUID): Flow<TerritoryReportHouse>
    fun getTerritoryReportHouses(territoryId: UUID, territoryStreetId: UUID? = null):
            Flow<List<TerritoryReportHouse>>

    fun getTerritoryReportRooms(territoryId: UUID, houseId: UUID? = null):
            Flow<List<TerritoryReportRoom>>

    fun getTerritoryReportRoom(roomId: UUID): Flow<TerritoryReportRoom>
    fun process(territoryReportId: UUID): Flow<UUID>
    fun cancelProcess(territoryReportId: UUID): Flow<UUID>
    fun save(territoryMemberReport: TerritoryMemberReport): Flow<TerritoryMemberReport>
    fun save(territoryReportHouse: TerritoryReportHouse): Flow<TerritoryReportHouse>
    fun save(territoryReportRoom: TerritoryReportRoom): Flow<TerritoryReportRoom>
    fun delete(territoryMemberReport: TerritoryMemberReport): Flow<TerritoryMemberReport>
    fun deleteById(territoryReportId: UUID): Flow<UUID>
    suspend fun deleteAll()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractTerritoryReports(): Flow<List<TerritoryMemberReportCsv>>
    fun loadTerritoryReports(territoryReports: List<TerritoryMemberReportCsv>): Flow<Int>
}