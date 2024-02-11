package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.territory.Entrance
import com.oborodulin.jwsuite.domain.model.territory.Floor
import com.oborodulin.jwsuite.domain.model.territory.House
import com.oborodulin.jwsuite.domain.model.territory.Room
import com.oborodulin.jwsuite.domain.model.territory.Territory
import com.oborodulin.jwsuite.domain.model.territory.TerritoryLocation
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreet
import com.oborodulin.jwsuite.domain.model.territory.TerritoryTotals
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.territory.CongregationTerritoryCrossRefCsv
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryCsv
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryMemberCrossRefCsv
import com.oborodulin.jwsuite.domain.types.TerritoryLocationType
import com.oborodulin.jwsuite.domain.types.TerritoryProcessType
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime
import java.util.UUID

interface TerritoriesRepository : CsvTransferableRepo {
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
    fun getTerritoryStreetHouses(territoryId: UUID): Flow<List<TerritoryStreet>>

    // Territory Houses:
    fun getHouses(territoryId: UUID): Flow<List<House>>
    fun getEntrances(territoryId: UUID): Flow<List<Entrance>>
    fun getFloors(territoryId: UUID): Flow<List<Floor>>
    fun getRooms(territoryId: UUID): Flow<List<Room>>

    fun get(territoryId: UUID): Flow<Territory>
    fun getFavoriteTotals(): Flow<TerritoryTotals?>
    fun save(territory: Territory): Flow<Territory>
    fun delete(territory: Territory): Flow<Territory>
    fun delete(territoryId: UUID): Flow<UUID>
    suspend fun deleteAll()

    // API:
    fun handOutTerritories(
        memberId: UUID, territoryIds: List<UUID> = emptyList(), receivingDate: OffsetDateTime
    ): Flow<List<UUID>>

    fun processTerritories(
        territoryIds: List<UUID> = emptyList(), deliveryDate: OffsetDateTime
    ): Flow<List<UUID>>

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractTerritories(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<TerritoryCsv>>

    fun extractCongregationTerritories(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<CongregationTerritoryCrossRefCsv>>

    fun extractTerritoryMembers(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<TerritoryMemberCrossRefCsv>>

    fun loadTerritories(territories: List<TerritoryCsv>): Flow<Int>
    fun loadCongregationTerritories(congregationTerritories: List<CongregationTerritoryCrossRefCsv>): Flow<Int>
    fun loadTerritoryMembers(territoryMembers: List<TerritoryMemberCrossRefCsv>): Flow<Int>
}