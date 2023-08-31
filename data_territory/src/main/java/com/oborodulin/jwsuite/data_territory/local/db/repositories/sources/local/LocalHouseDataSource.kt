package com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.local

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
    fun getHouse(houseId: UUID): Flow<HouseView>
    suspend fun insertHouse(house: HouseEntity)
    suspend fun updateHouse(house: HouseEntity)
    suspend fun deleteHouse(house: HouseEntity)
    suspend fun deleteHouseById(houseId: UUID)
    suspend fun deleteHouses(houses: List<HouseEntity>)
    suspend fun deleteAllHouses()
}