package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.geo.GeoStreet
import com.oborodulin.jwsuite.domain.model.territory.Entrance
import com.oborodulin.jwsuite.domain.model.territory.Floor
import com.oborodulin.jwsuite.domain.model.territory.House
import com.oborodulin.jwsuite.domain.model.territory.Room
import com.oborodulin.jwsuite.domain.model.territory.Territory
import com.oborodulin.jwsuite.domain.model.territory.TerritoryLocation
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreet
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreetNamesAndHouseNums
import com.oborodulin.jwsuite.domain.types.TerritoryLocationType
import com.oborodulin.jwsuite.domain.types.TerritoryProcessType
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

    fun getAllByGeo(
        localityId: UUID, localityDistrictId: UUID? = null, microdistrictId: UUID? = null
    ): Flow<List<Territory>>

    fun getAllForHouse(houseId: UUID): Flow<List<Territory>>
    fun getNextNum(congregationId: UUID, territoryCategoryId: UUID): Flow<Int>

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

    fun processTerritories(
        territoryIds: List<UUID> = emptyList(), deliveryDate: OffsetDateTime
    ): Flow<List<UUID>>
}