package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data.local.db.views.MemberView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalMemberDataSource {
    fun getFavoriteCongregationMembers(): Flow<List<MemberView>>
    fun getCongregationMembers(congregationId: UUID): Flow<List<MemberView>>
    fun getGroupMembers(groupId: UUID): Flow<List<MemberView>>
    fun getMember(memberId: UUID): Flow<MemberView>
    suspend fun insertMember(member: MemberEntity)
    suspend fun updateMember(member: MemberEntity)
    suspend fun deleteMember(member: MemberEntity)
    suspend fun deleteMemberById(memberId: UUID)
    suspend fun deleteMembers(members: List<MemberEntity>)
    suspend fun deleteAllMembers()
}