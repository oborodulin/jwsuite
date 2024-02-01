package com.oborodulin.jwsuite.data_territory.local.db.repositories.sources

import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.HouseView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetHouseView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalHouseDataSource {
    fun getAllHouses(): Flow<List<HouseView>>
    fun getStreetHouses(streetId: UUID): Flow<List<HouseView>>
    fun getTerritoryHouses(territoryId: UUID): Flow<List<HouseView>>
    fun getTerritoryStreetHouses(territoryId: UUID): Flow<List<TerritoryStreetHouseView>>
    fun getHousesForTerritory(territoryId: UUID): Flow<List<HouseView>>
    fun getHouse(houseId: UUID): Flow<HouseView>
    fun isTerritoryStreetExistsHouses(territoryStreetId: UUID): Flow<Boolean>
    suspend fun insertHouse(house: HouseEntity)
    suspend fun updateHouse(house: HouseEntity)
    suspend fun deleteHouse(house: HouseEntity)
    suspend fun deleteHouseById(houseId: UUID)
    suspend fun deleteHouses(houses: List<HouseEntity>)
    suspend fun deleteAllHouses()

    fun getNextHouseNum(streetId: UUID): Int
    suspend fun clearTerritoryById(houseId: UUID)
    suspend fun setTerritoryById(houseId: UUID, territoryId: UUID)

    // -------------------------------------- CSV Transfer --------------------------------------
    fun getHouseEntities(): Flow<List<HouseEntity>>
    suspend fun loadHouseEntities(houses: List<HouseEntity>)
}