package com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources

import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleTransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.TransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleTransferObjectView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalMemberDataSource {
    // Members by Congregation:
    fun getFavoriteCongregationMembers(): Flow<List<MemberView>>
    fun getCongregationMembers(congregationId: UUID): Flow<List<MemberView>>
    fun getFavoriteCongregationGroupMembers(): Flow<List<MemberView>>

    // Members by Groups:
    fun getGroupMembers(groupId: UUID): Flow<List<MemberView>>
    fun getEmptyGroupMembers(congregationId: UUID? = null): Flow<List<MemberView>>

    // Roles:
    fun getMemberRoles(memberId: UUID): Flow<List<MemberRoleView>>
    fun getMemberRoles(pseudonym: String): Flow<List<MemberRoleView>>
    fun getRoles(memberId: UUID): Flow<List<RoleEntity>>
    fun getRoles(pseudonym: String): Flow<List<RoleEntity>>
    fun getRolesForMember(memberId: UUID): Flow<List<RoleEntity>>

    // Transfer Objects:
    fun getMemberTransferObjects(pseudonym: String): Flow<List<MemberRoleTransferObjectView>>
    fun getRoleTransferObjects(roleId: UUID): Flow<List<MemberRoleTransferObjectView>>
    fun getTransferObjectsForRole(roleId: UUID): Flow<List<TransferObjectEntity>>

    // Member:
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
    fun getMemberRole(memberRoleId: UUID): Flow<MemberRoleView>
    suspend fun insertMemberRole(memberRole: MemberRoleEntity)
    suspend fun updateMemberRole(memberRole: MemberRoleEntity)
    suspend fun deleteMemberRoleById(memberRoleId: UUID)

    // Movements:
    suspend fun deleteMovementById(memberMovementId: UUID)

    // Transfer Objects:
    suspend fun insertRoleTransferObject(roleTransferObject: RoleTransferObjectEntity)
    suspend fun updateRoleTransferObject(roleTransferObject: RoleTransferObjectEntity)
    suspend fun deleteRoleTransferObjectById(roleTransferObjectId: UUID)
}