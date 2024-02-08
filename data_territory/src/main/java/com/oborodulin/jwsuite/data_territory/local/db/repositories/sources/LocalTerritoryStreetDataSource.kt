package com.oborodulin.jwsuite.data_territory.local.db.repositories.sources

import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetNamesAndHouseNumsView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalTerritoryStreetDataSource {
    fun getTerritoryStreet(territoryStreetId: UUID): Flow<TerritoryStreetView>
    fun getTerritoryStreets(territoryId: UUID): Flow<List<TerritoryStreetView>>

    fun getStreetsForTerritory(territoryId: UUID): Flow<List<GeoStreetView>>

    fun getTerritoryStreetNamesAndHouseNums(congregationId: UUID? = null): Flow<List<TerritoryStreetNamesAndHouseNumsView>>

    //fun getTerritoryStreetNames(territoryId: UUID): Flow<String?>
    suspend fun insertStreet(
        territory: TerritoryEntity, street: GeoStreetEntity,
        isEvenSide: Boolean? = null, isPrivateSector: Boolean? = null, estimatedHouses: Int? = null
    )

    suspend fun insertStreet(territoryStreet: TerritoryStreetEntity)

    suspend fun updateStreet(territoryStreet: TerritoryStreetEntity)
    suspend fun deleteStreet(territoryStreet: TerritoryStreetEntity)
    suspend fun deleteStreet(territoryStreetId: UUID)
    suspend fun deleteStreets(territoryId: UUID)

    // -------------------------------------- CSV Transfer --------------------------------------
    fun getTerritoryStreetEntities(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<TerritoryStreetEntity>>

    suspend fun loadTerritoryStreetEntities(territoryStreets: List<TerritoryStreetEntity>)
}