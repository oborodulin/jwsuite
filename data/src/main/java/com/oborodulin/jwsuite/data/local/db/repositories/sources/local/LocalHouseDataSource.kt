package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data.local.db.entities.HouseEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalHouseDataSource {
    fun getStreetHouses(streetId: UUID): Flow<List<HouseEntity>>
    fun getTerritoryHouses(territoryId: UUID): Flow<List<HouseEntity>>
    fun getHouse(houseId: UUID): Flow<HouseEntity>
    suspend fun insertHouse(house: HouseEntity)
    suspend fun updateHouse(house: HouseEntity)
    suspend fun deleteHouse(house: HouseEntity)
    suspend fun deleteHouseById(houseId: UUID)
    suspend fun deleteHouses(houses: List<HouseEntity>)
    suspend fun deleteAllHouses()
}