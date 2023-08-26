package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.Entrance
import com.oborodulin.jwsuite.domain.model.Floor
import com.oborodulin.jwsuite.domain.model.GeoStreet
import com.oborodulin.jwsuite.domain.model.House
import com.oborodulin.jwsuite.domain.model.Room
import com.oborodulin.jwsuite.domain.model.Territory
import com.oborodulin.jwsuite.domain.model.TerritoryLocation
import com.oborodulin.jwsuite.domain.model.TerritoryStreet
import com.oborodulin.jwsuite.domain.model.TerritoryStreetNamesAndHouseNums
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import com.oborodulin.jwsuite.domain.util.TerritoryProcessType
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime
import java.util.UUID

interface TerritoriesRepository {
    fun getCongregationTerritories(congregationId: UUID? = null): Flow<List<Territory>>
    fun getCongregationTerritoryLocations(isPrivateSector: Boolean, congregationId: UUID? = null):
            Flow<List<TerritoryLocation>>

    fun getTerritories(
        territoryProcessType: TerritoryProcessType, territoryLocationType: TerritoryLocationType,
        locationId: UUID? = null, isPrivateSector: Boolean, congregationId: UUID? = null
    ): Flow<List<Territory>>

    // Territory Streets:
    fun getTerritoryStreet(territoryStreetId: UUID): Flow<TerritoryStreet>
    fun getTerritoryStreets(territoryId: UUID): Flow<List<TerritoryStreet>>
    fun getTerritoryStreetHouses(territoryId: UUID): Flow<List<TerritoryStreet>>
    fun getStreetsForTerritory(territoryId: UUID): Flow<List<GeoStreet>>
    fun saveTerritoryStreet(territoryStreet: TerritoryStreet): Flow<TerritoryStreet>
    fun deleteTerritoryStreetById(territoryStreetId: UUID): Flow<UUID>

    // Territory Houses:
    fun getHouses(territoryId: UUID): Flow<List<House>>
    fun getEntrances(territoryId: UUID): Flow<List<Entrance>>
    fun getFloors(territoryId: UUID): Flow<List<Floor>>
    fun getRooms(territoryId: UUID): Flow<List<Room>>
    fun getTerritoryStreetNamesAndHouseNums(congregationId: UUID? = null): Flow<List<TerritoryStreetNamesAndHouseNums>>
    fun get(territoryId: UUID): Flow<Territory>
    fun save(territory: Territory): Flow<Territory>
    fun delete(territory: Territory): Flow<Territory>
    fun deleteById(territoryId: UUID): Flow<UUID>
    suspend fun deleteAll()

    // API:
    fun handOutTerritories(
        memberId: UUID, territoryIds: List<UUID> = emptyList(), receivingDate: OffsetDateTime
    ): Flow<List<UUID>>
}