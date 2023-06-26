package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data.local.db.views.HouseView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalHouseDataSource {
    fun getStreetHouses(streetId: UUID): Flow<List<HouseView>>
    fun getTerritoryHouses(territoryId: UUID): Flow<List<HouseView>>
    fun getHouse(houseId: UUID): Flow<HouseView>
    suspend fun insertHouse(house: HouseEntity)
    suspend fun updateHouse(house: HouseEntity)
    suspend fun deleteHouse(house: HouseEntity)
    suspend fun deleteHouseById(houseId: UUID)
    suspend fun deleteHouses(houses: List<HouseEntity>)
    suspend fun deleteAllHouses()
}