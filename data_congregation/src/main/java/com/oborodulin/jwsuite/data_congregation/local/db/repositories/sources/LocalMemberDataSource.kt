package com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources

import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleTransferObjectView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView
import com.oborodulin.jwsuite.data_congregation.local.db.views.RoleTransferObjectView
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalMemberDataSource {
    // Members by Congregation:
    fun getFavoriteCongregationMembers(isService: Boolean): Flow<List<MemberView>>
    fun getCongregationMembers(congregationId: UUID, isService: Boolean): Flow<List<MemberView>>

    // Members by Groups:
    fun getFavoriteCongregationGroupMembers(isService: Boolean): Flow<List<MemberView>>
    fun getGroupMembers(groupId: UUID, isService: Boolean): Flow<List<MemberView>>
    fun getEmptyGroupMembers(
        congregationId: UUID? = null,
        isService: Boolean
    ): Flow<List<MemberView>>

    // Member by Roles:
    fun getMembersByRoles(roleTypes: List<MemberRoleType> = emptyList()): Flow<List<MemberView>>

    // Member Roles:
    fun getMemberRoles(memberId: UUID): Flow<List<MemberRoleView>>
    fun getMemberRoles(pseudonym: String): Flow<List<MemberRoleView>>

    // Roles:
    fun getRoles(pseudonym: String): Flow<List<RoleEntity>>
    fun getRolesForMember(memberId: UUID): Flow<List<RoleEntity>>

    // Transfer Objects:
    fun getRoleTransferObjects(pseudonym: String): Flow<List<RoleTransferObjectView>>
    fun getMemberRoleTransferObjects(roleId: UUID): Flow<List<MemberRoleTransferObjectView>>

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
    // Member Roles:
    fun getMemberRole(memberRoleId: UUID): Flow<MemberRoleView>
    suspend fun insertMemberRole(memberRole: MemberRoleEntity)
    suspend fun updateMemberRole(memberRole: MemberRoleEntity)
    suspend fun deleteMemberRoleById(memberRoleId: UUID)

    // Movements:
    suspend fun deleteMovementById(memberMovementId: UUID)

    // -------------------------------------- CSV Transfer --------------------------------------
    fun getMemberEntities(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<MemberEntity>>

    fun getMemberCongregationEntities(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<MemberCongregationCrossRefEntity>>

    fun getMemberMovementEntities(byFavorite: Boolean = false): Flow<List<MemberMovementEntity>>
    fun getMemberRoleEntities(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<MemberRoleEntity>>

    suspend fun loadMemberEntities(members: List<MemberEntity>)
    suspend fun loadMemberCongregationEntities(memberCongregations: List<MemberCongregationCrossRefEntity>)
    suspend fun loadMemberMovementEntities(memberMovements: List<MemberMovementEntity>)
    suspend fun loadMemberRoleEntities(memberRoles: List<MemberRoleEntity>)
}