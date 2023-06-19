package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data.local.db.entities.EntranceEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalEntranceDataSource {
    fun getHouseEntrances(houseId: UUID): Flow<List<EntranceEntity>>
    fun getTerritoryEntrances(territoryId: UUID): Flow<List<EntranceEntity>>
    fun getEntrance(entranceId: UUID): Flow<EntranceEntity>
    suspend fun insertEntrance(entrance: EntranceEntity)
    suspend fun updateEntrance(entrance: EntranceEntity)
    suspend fun deleteEntrance(entrance: EntranceEntity)
    suspend fun deleteEntranceById(entranceId: UUID)
    suspend fun deleteEntrances(entrances: List<EntranceEntity>)
    suspend fun deleteAllEntrances()
}