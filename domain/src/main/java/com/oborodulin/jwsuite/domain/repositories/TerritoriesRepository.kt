package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.Entrance
import com.oborodulin.jwsuite.domain.model.Floor
import com.oborodulin.jwsuite.domain.model.House
import com.oborodulin.jwsuite.domain.model.Room
import com.oborodulin.jwsuite.domain.model.Territory
import com.oborodulin.jwsuite.domain.model.TerritoryLocation
import com.oborodulin.jwsuite.domain.model.TerritoryStreet
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import com.oborodulin.jwsuite.domain.util.TerritoryProcessType
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TerritoriesRepository {
    fun getCongregationTerritories(congregationId: UUID? = null): Flow<List<Territory>>
    fun getCongregationTerritoryLocations(isPrivateSector: Boolean, congregationId: UUID? = null):
            Flow<List<TerritoryLocation>>

    fun getTerritories(
        territoryProcessType: TerritoryProcessType, territoryLocationType: TerritoryLocationType,
        locationId: UUID? = null, isPrivateSector: Boolean, congregationId: UUID? = null
    ): Flow<List<Territory>>

    fun getTerritoryStreets(territoryId: UUID): Flow<List<TerritoryStreet>>
    fun getHouses(territoryId: UUID): Flow<List<House>>
    fun getEntrances(territoryId: UUID): Flow<List<Entrance>>
    fun getFloors(territoryId: UUID): Flow<List<Floor>>
    fun getRooms(territoryId: UUID): Flow<List<Room>>
    fun get(territoryId: UUID): Flow<Territory>
    fun save(territory: Territory): Flow<Territory>
    fun delete(territory: Territory): Flow<Territory>
    fun deleteById(territoryId: UUID): Flow<UUID>
    suspend fun deleteAll()
}