package com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources

import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalMemberDataSource {
    fun getFavoriteCongregationMembers(): Flow<List<MemberView>>
    fun getCongregationMembers(congregationId: UUID): Flow<List<MemberView>>
    fun getFavoriteCongregationGroupMembers(): Flow<List<MemberView>>
    fun getGroupMembers(groupId: UUID): Flow<List<MemberView>>
    fun getMember(memberId: UUID): Flow<MemberView>
    suspend fun insertMember(member: MemberEntity, memberMovement: MemberMovementEntity)
    suspend fun updateMember(member: MemberEntity, memberMovement: MemberMovementEntity)
    suspend fun deleteMember(member: MemberEntity)
    suspend fun deleteMemberById(memberId: UUID)
    suspend fun deleteMembers(members: List<MemberEntity>)
    suspend fun deleteAllMembers()

    // Movements:
    suspend fun deleteMovementById(memberMovementId: UUID)
}