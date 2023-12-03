package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole
import com.oborodulin.jwsuite.domain.model.congregation.Role
import com.oborodulin.jwsuite.domain.model.congregation.RoleTransferObject
import com.oborodulin.jwsuite.domain.model.congregation.TransferObject
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface MembersRepository {
    // Members by Congregation:
    fun getAllByFavoriteCongregation(isService: Boolean = false): Flow<List<Member>>
    fun getAllByCongregation(
        congregationId: UUID, isService: Boolean = false
    ): Flow<List<Member>>

    // Members by Groups:
    fun getAllByFavoriteCongregationGroup(isService: Boolean = false): Flow<List<Member>>
    fun getAllByGroup(groupId: UUID, isService: Boolean = false): Flow<List<Member>>

    // Member Roles:
    fun getMemberRoles(memberId: UUID): Flow<List<MemberRole>>
    fun getMemberRoles(pseudonym: String): Flow<List<MemberRole>>

    // Roles:
    fun getAllRoles(): Flow<List<Role>>
    fun getRoles(pseudonym: String): Flow<List<Role>>
    fun getRolesForMember(memberId: UUID): Flow<List<Role>>

    // Transfer Objects:
    fun getMemberTransferObjects(pseudonym: String): Flow<List<RoleTransferObject>>
    fun getRoleTransferObjects(roleId: UUID): Flow<List<RoleTransferObject>>
    fun getTransferObjectsForRole(roleId: UUID): Flow<List<TransferObject>>

    // Member:
    fun get(memberId: UUID): Flow<Member>
    fun save(member: Member): Flow<Member>
    fun delete(member: Member): Flow<Member>
    fun deleteById(memberId: UUID): Flow<UUID>
    suspend fun deleteAll()

    // Member Role:
    fun getMemberRole(memberRoleId: UUID): Flow<MemberRole>
    fun saveMemberRole(role: MemberRole): Flow<MemberRole>
    fun deleteMemberRoleById(memberRoleId: UUID): Flow<UUID>

    // Movements:
    fun deleteMovementById(memberMovementId: UUID): Flow<UUID>
}