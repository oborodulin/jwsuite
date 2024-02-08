package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.territory.Floor
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.territory.FloorCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface FloorsRepository : CsvTransferableRepo {
    fun getAll(): Flow<List<Floor>>
    fun getAllByHouse(houseId: UUID): Flow<List<Floor>>
    fun getAllByEntrance(entranceId: UUID): Flow<List<Floor>>
    fun getAllByTerritory(territoryId: UUID): Flow<List<Floor>>
    fun getAllForTerritory(territoryId: UUID): Flow<List<Floor>>
    fun getNext(floorId: UUID): Flow<Floor?>
    fun get(floorId: UUID): Flow<Floor>
    fun isExistsInHouse(houseId: UUID): Flow<Boolean>
    fun isExistsInEntrance(entranceId: UUID): Flow<Boolean>
    fun save(floor: Floor): Flow<Floor>
    fun delete(floor: Floor): Flow<Floor>
    fun deleteById(floorId: UUID): Flow<UUID>
    suspend fun deleteAll()

    fun getNextNum(houseId: UUID): Flow<Int>
    fun clearTerritory(floorId: UUID): Flow<UUID>
    fun setTerritory(floorIds: List<UUID> = emptyList(), territoryId: UUID): Flow<List<UUID>>

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractFloors(username: String? = null, byFavorite: Boolean = false): Flow<List<FloorCsv>>
    fun loadFloors(floors: List<FloorCsv>): Flow<Int>
}