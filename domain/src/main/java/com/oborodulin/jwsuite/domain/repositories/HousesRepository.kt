package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.territory.House
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.territory.HouseCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface HousesRepository : CsvTransferableRepo {
    fun getAll(): Flow<List<House>>
    fun getAllByStreet(streetId: UUID): Flow<List<House>>
    fun getAllByTerritory(territoryId: UUID): Flow<List<House>>
    fun getAllForTerritory(territoryId: UUID): Flow<List<House>>
    fun getNext(houseId: UUID): Flow<House?>
    fun get(houseId: UUID): Flow<House>
    fun isExistsInTerritoryStreet(territoryStreetId: UUID): Flow<Boolean>
    fun save(house: House): Flow<House>
    fun delete(house: House): Flow<House>
    fun delete(houseId: UUID): Flow<UUID>
    suspend fun deleteAll()

    fun getNextNum(streetId: UUID): Flow<Int>
    fun clearTerritory(houseId: UUID): Flow<UUID>
    fun setTerritory(houseIds: List<UUID> = emptyList(), territoryId: UUID): Flow<List<UUID>>

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractHouses(username: String? = null, byFavorite: Boolean = false): Flow<List<HouseCsv>>
    fun loadHouses(houses: List<HouseCsv>): Flow<Int>
}