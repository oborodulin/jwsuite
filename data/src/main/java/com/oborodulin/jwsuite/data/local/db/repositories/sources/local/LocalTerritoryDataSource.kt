package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.data.local.db.views.TerritoriesAtWorkView
import com.oborodulin.jwsuite.data.local.db.views.TerritoriesHandOutView
import com.oborodulin.jwsuite.data.local.db.views.TerritoriesIdleView
import com.oborodulin.jwsuite.data.local.db.views.TerritoryLocationView
import com.oborodulin.jwsuite.data.local.db.views.TerritoryStreetNamesAndHouseNumsView
import com.oborodulin.jwsuite.data.local.db.views.TerritoryStreetView
import com.oborodulin.jwsuite.data.local.db.views.TerritoryView
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
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

    //    fun getTerritoryInfo(territoryId: UUID): Flow<List<TerritoryView>>
    fun getTerritoryStreetNamesAndHouseNums(congregationId: UUID? = null): Flow<List<TerritoryStreetNamesAndHouseNumsView>>
    fun getTerritory(territoryId: UUID): Flow<TerritoryView>
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

    // Streets:
    fun getTerritoryStreets(territoryId: UUID): Flow<List<TerritoryStreetView>>

    //fun getTerritoryStreetNames(territoryId: UUID): Flow<String?>
    suspend fun insertStreet(
        territory: TerritoryEntity, street: GeoStreetEntity,
        isEven: Boolean? = null, isPrivateSector: Boolean? = null, estimatedHouses: Int? = null
    )

    suspend fun updateStreet(territoryStreet: TerritoryStreetEntity)
    suspend fun deleteStreet(territoryStreet: TerritoryStreetEntity)
    suspend fun deleteStreet(territoryStreetId: UUID)
    suspend fun deleteStreets(territoryId: UUID)

    // API:
    suspend fun handOut(
        territoryId: UUID, memberId: UUID, receivingDate: OffsetDateTime = OffsetDateTime.now()
    )
}