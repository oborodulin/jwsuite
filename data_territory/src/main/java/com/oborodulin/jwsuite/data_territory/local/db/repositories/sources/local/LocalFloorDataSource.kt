package com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.FloorView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalFloorDataSource {
    fun getAllFloors(): Flow<List<FloorView>>
    fun getHouseFloors(houseId: UUID): Flow<List<FloorView>>
    fun getEntranceFloors(entranceId: UUID): Flow<List<FloorView>>
    fun getTerritoryFloors(territoryId: UUID): Flow<List<FloorView>>
    fun getFloor(floorId: UUID): Flow<FloorView>
    suspend fun insertFloor(floor: FloorEntity)
    suspend fun updateFloor(floor: FloorEntity)
    suspend fun deleteFloor(floor: FloorEntity)
    suspend fun deleteFloorById(floorId: UUID)
    suspend fun deleteFloors(floors: List<FloorEntity>)
    suspend fun deleteAllFloors()
}