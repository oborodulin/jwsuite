package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data.local.db.entities.MemberEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalMemberDataSource {
    fun getCongregationMembers(congregationId: UUID): Flow<List<MemberEntity>>
    fun getGroupMembers(groupId: UUID): Flow<List<MemberEntity>>
    fun getMember(memberId: UUID): Flow<MemberEntity>
    suspend fun insertGroup(member: MemberEntity)
    suspend fun updateGroup(member: MemberEntity)
    suspend fun deleteGroup(member: MemberEntity)
    suspend fun deleteGroupById(memberId: UUID)
    suspend fun deleteGroups(members: List<MemberEntity>)
    suspend fun deleteAllGroups()
}