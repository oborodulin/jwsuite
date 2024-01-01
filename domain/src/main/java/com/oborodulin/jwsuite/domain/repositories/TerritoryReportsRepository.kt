package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportHouse
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportRoom
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TerritoryReportsRepository {
    fun getAll(): Flow<List<TerritoryMemberReport>>
    fun get(territoryReportId: UUID): Flow<TerritoryMemberReport>
    fun getAllByTerritoryStreet(territoryStreetId: UUID): Flow<List<TerritoryMemberReport>>
    fun getAllByHouse(houseId: UUID): Flow<List<TerritoryMemberReport>>
    fun getAllByRoom(roomId: UUID): Flow<List<TerritoryMemberReport>>
    fun getTerritoryReportHouses(territoryId: UUID, territoryStreetId: UUID? = null):
            Flow<List<TerritoryReportHouse>>

    fun getTerritoryReportRooms(territoryId: UUID, houseId: UUID? = null):
            Flow<List<TerritoryReportRoom>>

    fun save(territoryReportHouse: TerritoryReportHouse): Flow<TerritoryReportHouse>
    fun save(territoryReportRoom: TerritoryReportRoom): Flow<TerritoryReportRoom>
    fun delete(territoryMemberReport: TerritoryMemberReport): Flow<TerritoryMemberReport>
    fun deleteById(territoryReportId: UUID): Flow<UUID>
    suspend fun deleteAll()
}