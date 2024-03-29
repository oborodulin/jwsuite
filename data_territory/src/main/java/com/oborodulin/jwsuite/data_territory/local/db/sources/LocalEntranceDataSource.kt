package com.oborodulin.jwsuite.data_territory.local.db.sources

import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.EntranceView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalEntranceDataSource {
    fun getAllEntrances(): Flow<List<EntranceView>>
    fun getHouseEntrances(houseId: UUID): Flow<List<EntranceView>>
    fun getTerritoryEntrances(territoryId: UUID): Flow<List<EntranceView>>
    fun getEntrancesForTerritory(territoryId: UUID): Flow<List<EntranceView>>
    fun getNextEntrance(entranceId: UUID): Flow<EntranceView?>
    fun getEntrance(entranceId: UUID): Flow<EntranceView>
    fun isHouseExistsRooms(houseId: UUID): Flow<Boolean>
    suspend fun insertEntrance(entrance: EntranceEntity)
    suspend fun updateEntrance(entrance: EntranceEntity)
    suspend fun deleteEntrance(entrance: EntranceEntity)
    suspend fun deleteEntranceById(entranceId: UUID)
    suspend fun deleteEntrances(entrances: List<EntranceEntity>)
    suspend fun deleteAllEntrances()

    fun getNextEntranceNum(houseId: UUID): Int
    suspend fun clearTerritoryById(entranceId: UUID)
    suspend fun setTerritoryById(entranceId: UUID, territoryId: UUID)

    // -------------------------------------- CSV Transfer --------------------------------------
    fun getEntranceEntities(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<EntranceEntity>>

    suspend fun loadEntranceEntities(entrances: List<EntranceEntity>)
}