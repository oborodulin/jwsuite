package com.oborodulin.jwsuite.data_territory.local.db.sources

import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoriesAtWorkView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoriesHandOutView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoriesIdleView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryLocationView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryTotalView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryView
import com.oborodulin.jwsuite.domain.types.TerritoryLocationType
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime
import java.util.UUID

interface LocalTerritoryDataSource {
    // Territories:
    fun getCongregationTerritories(congregationId: UUID): Flow<List<TerritoryView>>
    fun getFavoriteCongregationTerritories(): Flow<List<TerritoryView>>
    fun getCongregationTerritoryLocations(isPrivateSector: Boolean, congregationId: UUID? = null):
            Flow<List<TerritoryLocationView>>

    fun getHandOutTerritories(
        congregationId: UUID? = null, isPrivateSector: Boolean? = null,
        territoryLocationType: TerritoryLocationType, locationId: UUID? = null
    ): Flow<List<TerritoriesHandOutView>>

    fun getAtWorkTerritories(
        congregationId: UUID? = null, isPrivateSector: Boolean? = null,
        territoryLocationType: TerritoryLocationType, locationId: UUID? = null
    ): Flow<List<TerritoriesAtWorkView>>

    fun getIdleTerritories(
        congregationId: UUID? = null, isPrivateSector: Boolean? = null,
        territoryLocationType: TerritoryLocationType, locationId: UUID? = null
    ): Flow<List<TerritoriesIdleView>>

    fun getTerritoriesByGeo(
        localityId: UUID, localityDistrictId: UUID? = null, microdistrictId: UUID? = null
    ): Flow<List<TerritoryView>>

    fun getTerritoriesForHouse(houseId: UUID): Flow<List<TerritoryView>>

    fun getNextTerritoryNum(congregationId: UUID, territoryCategoryId: UUID): Int

    fun getTerritory(territoryId: UUID): Flow<TerritoryView>
    fun getFavoriteTerritoryTotals(): Flow<TerritoryTotalView?>
    suspend fun insertTerritory(territory: TerritoryEntity)
    suspend fun updateTerritory(territory: TerritoryEntity)
    suspend fun deleteTerritory(territory: TerritoryEntity)
    suspend fun deleteTerritoryById(territoryId: UUID)
    suspend fun deleteTerritories(territories: List<TerritoryEntity>)
    suspend fun deleteAllTerritories()

    // Congregations:
    suspend fun insertTerritoryToCongregation(
        congregation: CongregationEntity, territory: TerritoryEntity,
        startUsingDate: OffsetDateTime = OffsetDateTime.now()
    )

    suspend fun updateTerritoryInCongregation(congregationTerritory: CongregationTerritoryCrossRefEntity)
    suspend fun deleteTerritoryFromCongregation(congregationTerritory: CongregationTerritoryCrossRefEntity)
    suspend fun deleteTerritoryFromCongregation(congregationTerritoryId: UUID)
    suspend fun deleteAllTerritoriesFromCongregation(congregationId: UUID)

    // Members:
    suspend fun insertMember(
        territory: TerritoryEntity, member: MemberEntity,
        receivingDate: OffsetDateTime = OffsetDateTime.now()
    )

    suspend fun insertMember(
        territory: TerritoryEntity, memberId: UUID,
        receivingDate: OffsetDateTime = OffsetDateTime.now()
    )

    suspend fun insertMember(
        territoryId: UUID, memberId: UUID, receivingDate: OffsetDateTime = OffsetDateTime.now()
    )

    suspend fun updateMember(territoryMember: TerritoryMemberCrossRefEntity)
    suspend fun deleteMember(territoryMember: TerritoryMemberCrossRefEntity)
    suspend fun deleteMember(territoryMemberId: UUID)
    suspend fun deleteMembers(territoryId: UUID)

    // API:
    suspend fun handOut(
        territoryId: UUID, memberId: UUID, receivingDate: OffsetDateTime = OffsetDateTime.now()
    )

    suspend fun process(territoryId: UUID, deliveryDate: OffsetDateTime)

    // -------------------------------------- CSV Transfer --------------------------------------
    fun getTerritoryEntities(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<TerritoryEntity>>

    fun getCongregationTerritoryEntities(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<CongregationTerritoryCrossRefEntity>>

    fun getTerritoryMemberEntities(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<TerritoryMemberCrossRefEntity>>

    suspend fun loadTerritoryEntities(territories: List<TerritoryEntity>)
    suspend fun loadCongregationTerritoryEntities(congregationTerritories: List<CongregationTerritoryCrossRefEntity>)
    suspend fun loadTerritoryMemberEntities(territoryMembers: List<TerritoryMemberCrossRefEntity>)
}