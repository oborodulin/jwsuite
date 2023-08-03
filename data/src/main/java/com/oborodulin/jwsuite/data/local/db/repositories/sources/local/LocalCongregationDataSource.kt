package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data.local.db.entities.CongregationMemberCrossRefEntity
import com.oborodulin.jwsuite.data.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.data.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data.local.db.views.CongregationView
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime
import java.util.UUID

interface LocalCongregationDataSource {
    // Congregations:
    fun getCongregations(): Flow<List<CongregationView>>
    fun getCongregation(congregationId: UUID): Flow<CongregationView>
    fun getFavoriteCongregation(): Flow<CongregationView?>
    suspend fun insertCongregation(congregation: CongregationEntity)
    suspend fun updateCongregation(congregation: CongregationEntity)
    suspend fun deleteCongregation(congregation: CongregationEntity)
    suspend fun deleteCongregationById(congregationId: UUID)
    suspend fun makeFavoriteCongregationById(congregationId: UUID)
    suspend fun deleteCongregations(congregations: List<CongregationEntity>)
    suspend fun deleteAllCongregations()

    // Members:
    suspend fun insertMember(
        congregation: CongregationEntity, member: MemberEntity,
        activityDate: OffsetDateTime = OffsetDateTime.now()
    )

    suspend fun updateMember(congregationMember: CongregationMemberCrossRefEntity)
    suspend fun deleteMember(congregationMember: CongregationMemberCrossRefEntity)
    suspend fun deleteMember(congregationMemberId: UUID)
    suspend fun deleteMembers(congregationId: UUID)

    // Territories:
    suspend fun insertTerritory(
        congregation: CongregationEntity, territory: TerritoryEntity,
        startUsingDate: OffsetDateTime = OffsetDateTime.now()
    )

    suspend fun updateTerritory(congregationTerritory: CongregationTerritoryCrossRefEntity)
    suspend fun deleteTerritory(congregationTerritory: CongregationTerritoryCrossRefEntity)
    suspend fun deleteTerritory(congregationTerritoryId: UUID)
    suspend fun deleteTerritories(congregationId: UUID)
}