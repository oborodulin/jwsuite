package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data.local.db.entities.FloorEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalFloorDataSource {
    fun getAllFloors(): Flow<List<FloorEntity>>
    fun getHouseFloors(houseId: UUID): Flow<List<FloorEntity>>
    fun getEntranceFloors(entranceId: UUID): Flow<List<FloorEntity>>
    fun getTerritoryFloors(territoryId: UUID): Flow<List<FloorEntity>>
    fun getFloor(floorId: UUID): Flow<FloorEntity>
    suspend fun insertFloor(floor: FloorEntity)
    suspend fun updateFloor(floor: FloorEntity)
    suspend fun deleteFloor(floor: FloorEntity)
    suspend fun deleteFloorById(floorId: UUID)
    suspend fun deleteFloors(floors: List<FloorEntity>)
    suspend fun deleteAllFloors()
}