package com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources

import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalMemberDataSource {
    fun getFavoriteCongregationMembers(): Flow<List<MemberView>>
    fun getCongregationMembers(congregationId: UUID): Flow<List<MemberView>>
    fun getFavoriteCongregationGroupMembers(): Flow<List<MemberView>>
    fun getGroupMembers(groupId: UUID): Flow<List<MemberView>>
    fun getEmptyGroupMembers(congregationId: UUID? = null): Flow<List<MemberView>>
    fun getMemberRoles(memberId: UUID): Flow<List<MemberRoleView>>
    fun getMemberRoles(pseudonym: String): Flow<List<MemberRoleView>>
    fun getRolesForMember(memberId: UUID): Flow<List<RoleEntity>>
    fun getMember(memberId: UUID): Flow<MemberView>
    suspend fun insertMember(
        member: MemberEntity,
        memberCongregation: MemberCongregationCrossRefEntity,
        memberMovement: MemberMovementEntity
    )

    suspend fun updateMember(
        member: MemberEntity,
        memberCongregation: MemberCongregationCrossRefEntity,
        memberMovement: MemberMovementEntity
    )

    suspend fun deleteMember(member: MemberEntity)
    suspend fun deleteMemberById(memberId: UUID)
    suspend fun deleteMembers(members: List<MemberEntity>)
    suspend fun deleteAllMembers()

    // Congregations:
    // Roles:
    suspend fun insertMemberRole(memberRole: MemberRoleEntity)
    suspend fun updateMemberRole(memberRole: MemberRoleEntity)
    suspend fun deleteRoleById(memberRoleId: UUID)

    // Movements:
    suspend fun deleteMovementById(memberMovementId: UUID)
}