package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.data.local.db.views.TerritoryDistrictView
import com.oborodulin.jwsuite.data.local.db.views.TerritoryView
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime
import java.util.UUID

interface LocalTerritoryDataSource {
    // Territories:
    fun getCongregationTerritories(
        congregationId: UUID, isPrivateSector: Boolean? = null
    ): Flow<List<TerritoryView>>

    fun getFavoriteCongregationTerritories(isPrivateSector: Boolean? = null): Flow<List<TerritoryView>>
    fun getCongregationTerritoryDistricts(
        isPrivateSector: Boolean, congregationId: UUID
    ): Flow<List<TerritoryDistrictView>>

    fun getLocalityTerritories(
        localityId: UUID, isPrivateSector: Boolean, congregationId: UUID? = null
    ): Flow<List<TerritoryView>>

    fun getLocalityDistrictTerritories(
        localityDistrictId: UUID, isPrivateSector: Boolean, congregationId: UUID? = null
    ): Flow<List<TerritoryView>>

    fun getMicrodistrictTerritories(
        microdistrictId: UUID, isPrivateSector: Boolean, congregationId: UUID? = null
    ): Flow<List<TerritoryView>>

    //    fun getTerritoryInfo(territoryId: UUID): Flow<List<TerritoryView>>
    fun getTerritory(territoryId: UUID): Flow<TerritoryView>
    suspend fun insertTerritory(territory: TerritoryEntity)
    suspend fun updateTerritory(territory: TerritoryEntity)
    suspend fun deleteTerritory(territory: TerritoryEntity)
    suspend fun deleteTerritoryById(territoryId: UUID)
    suspend fun deleteTerritories(territories: List<TerritoryEntity>)
    suspend fun deleteAllTerritories()

    // Members:
    suspend fun insertMember(
        territory: TerritoryEntity, member: MemberEntity,
        receivingDate: OffsetDateTime = OffsetDateTime.now()
    )

    suspend fun updateMember(territoryMember: TerritoryMemberCrossRefEntity)
    suspend fun deleteMember(territoryMember: TerritoryMemberCrossRefEntity)
    suspend fun deleteMember(territoryMemberId: UUID)
    suspend fun deleteMembers(territoryId: UUID)

    // Streets:
    suspend fun insertStreet(
        territory: TerritoryEntity, street: GeoStreetEntity,
        isEven: Boolean? = null, isPrivateSector: Boolean? = null, estimatedHouses: Int? = null
    )

    suspend fun updateStreet(territoryStreet: TerritoryStreetEntity)
    suspend fun deleteStreet(territoryStreet: TerritoryStreetEntity)
    suspend fun deleteStreet(territoryStreetId: UUID)
    suspend fun deleteStreets(territoryId: UUID)
}